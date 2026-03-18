package com.system.shop.service.impl;

import com.system.shop.base.ServiceImpl;
import com.system.shop.bean.search.LogisticsSearch;
import com.system.shop.mapper.StoreLogisticsMapper;
import com.system.shop.entity.Logistics;
import com.system.shop.entity.StoreLogistics;
import com.system.shop.service.LogisticsService;
import com.system.shop.service.StoreLogisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StoreLogisticsServiceImpl extends ServiceImpl<StoreLogisticsMapper, StoreLogistics> implements StoreLogisticsService {

    @Autowired
    private LogisticsService logisticsService;

    @Override
    public Boolean saveOrUpdate(Long storeId, Long logisticsId, Integer status) {
        StoreLogistics bean = baseMapper.selectByStoreIdAndLogisticsId(storeId, logisticsId);
        if (bean != null) {
            bean.setStatus(status);
            return this.updateByIdSelective(bean);
        } else {
            StoreLogistics storeLogistics = new StoreLogistics(storeId, logisticsId, status);
            return this.insert(storeLogistics);
        }
    }

    @Override
    public List<StoreLogistics> selectList(Long storeId, LogisticsSearch search) {
        List<StoreLogistics> list = new ArrayList<>();
        List<Logistics> logistics = logisticsService.selectList(search.querymap());
        logistics.forEach(item -> {
            StoreLogistics bean = baseMapper.selectByStoreIdAndLogisticsId(storeId, item.getId());
            if (bean == null) {
                bean = new StoreLogistics(item);
            } else {
                bean.setLogisticsCompanyCode(item.getLogisticsCompanyCode());
                bean.setLogisticsCompanyName(item.getLogisticsCompanyName());
            }
            list.add(bean);
        });
        return list;
    }
}
