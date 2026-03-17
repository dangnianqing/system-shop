package com.system.shop.service.impl;

import com.system.shop.base.ServiceImpl;
import com.system.shop.mapper.ProductYearMapper;
import com.system.shop.entity.ProductYear;
import com.system.shop.service.ProductYearService;
import org.springframework.stereotype.Service;
@Service
public class ProductYearServiceImpl extends ServiceImpl<ProductYearMapper, ProductYear> implements ProductYearService{


    @Override
    public Boolean saveOrUpdate(ProductYear productYear) {
        return null;
    }
}
