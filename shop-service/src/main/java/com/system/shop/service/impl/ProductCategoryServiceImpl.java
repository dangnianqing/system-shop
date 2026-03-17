package com.system.shop.service.impl;

import com.system.shop.base.ServiceImpl;
import com.system.shop.redis.RedisUtil;
import com.system.shop.mapper.ProductCategoryMapper;
import com.system.shop.entity.ProductCategory;
import com.system.shop.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductCategoryServiceImpl extends ServiceImpl<ProductCategoryMapper, ProductCategory> implements ProductCategoryService {
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public List<ProductCategory> selectTreeList() {
        List<ProductCategory> list = this.selectList(null);
        return list.stream().filter(o -> o.getLevel() == 0).peek(o -> o.setChildren(initChild(o, list)))
                // 收集
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductCategory> selectListOneLevel() {
        Map<String, Object> map = new HashMap<String, Object>() {{
            put("parentId", 0);
        }};
        return this.selectList(map);
    }

    @Override
    public ProductCategory selectCategoryChildrenList(Long parentId) {
        ProductCategory bean = this.selectById(parentId);
        Map<String, Object> map = new HashMap<String, Object>() {{
            put("parentId", parentId);
        }};
        List<ProductCategory> list = this.selectList(map);
        list.forEach(productCategory -> {
            map.put("parentId", productCategory.getId());
            productCategory.setChildren(this.selectList(map));
        });
        bean.setChildren(list);
        return bean;
    }

    private List<ProductCategory> initChild(ProductCategory model, List<ProductCategory> productCategories) {
        return productCategories.stream().filter(o -> o.getParentId().equals(model.getId()))
                .peek(o -> o.setChildren(initChild(o, productCategories)))
//                .sorted(Comparator.comparing(ProductCategory::getIndex))
                .collect(Collectors.toList());
    }


}

