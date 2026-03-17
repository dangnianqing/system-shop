package com.system.shop.service.impl;

import com.system.shop.base.ServiceImpl;
import com.system.shop.mapper.ProductSpecMapper;
import com.system.shop.entity.ProductSpec;
import com.system.shop.service.ProductSpecService;
import org.springframework.stereotype.Service;


@Service
public class ProductSpecServiceImpl extends ServiceImpl<ProductSpecMapper, ProductSpec> implements ProductSpecService {

    @Override
    public Boolean saveOrUpdate(ProductSpec productSpec) {
        ProductSpec bean = baseMapper.selectById(productSpec.getId());
        if (bean == null) {
            return this.insert(productSpec);
        }
        bean.setName(productSpec.getName());
        return this.updateByIdSelective(productSpec);
    }


}