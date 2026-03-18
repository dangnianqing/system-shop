package com.system.shop.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.system.shop.base.ServiceImpl;
import com.system.shop.mapper.RegionMapper;
import com.system.shop.entity.Region;
import com.system.shop.service.RegionService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegionServiceImpl extends ServiceImpl<RegionMapper, Region> implements RegionService {


    @Override
    public Boolean initData() {
        String json = HttpUtil.get("https://restapi.amap.com/v3/config/district?key=53e76bc006981fb39a7d7d03fa346b4d&keywords=100000&subdistrict=4");
        JSONObject jsonObject = JSON.parseObject(json);
        JSONArray jsonArray = jsonObject.getJSONArray("districts");
        List<Region> list = jsonArray.toJavaList(Region.class);
        for (Region country : list) {
            List<Region> provinces = country.getDistricts();
            for (Region province : provinces) {
                province.setParentId(100000L);
                this.insert(province);
                List<Region> citys = province.getDistricts();
                for (Region city : citys) {
                    city.setParentId(province.getId());
                    this.insert(city);
                    List<Region> countys = city.getDistricts();
                    for (Region county : countys) {
                        county.setParentId(city.getId());
                        this.insert(county);
                        List<Region> streets = county.getDistricts();
                        for (Region street : streets) {
                            street.setParentId(county.getId());
                        }
                        this.batchInsert(streets);

                    }

                }
            }


        }
        return list.size() > 0;
    }


    @Override
    public List<Region> selectByParentId(Long parentId) {
        return baseMapper.selectList(new HashMap<String, Object>() {{
            put("parentId", parentId);
        }});
    }

    @Override
    public List<Region> selectListTree() {
        List<Region> list = this.selectList(null);
        list.stream().filter(o -> o.getParentId() == 100000L).peek(o -> o.setChildren(initChild(o, list)))
                // 收集
                .collect(Collectors.toList());
        return list;

    }

    @Override
    public List<Region> selectCity() {
        List<Region> provinceList = baseMapper.selectList(new HashMap<String, Object>() {{
            put("level", "province");
        }});
        provinceList.forEach(region -> {
            List<Region> list = this.selectList(new HashMap<String, Object>() {{
                put("parentId", region.getId());
            }});
            region.setChildren(list);
        });
        return provinceList;
    }

    private List<Region> initChild(Region model, List<Region> regions) {
        return regions.stream().filter(o -> o.getParentId().equals(model.getId()))
                .peek(o -> o.setChildren(initChild(o, regions)))
                .sorted(Comparator.comparing(Region::getAdcode))
                .collect(Collectors.toList());
    }
}

