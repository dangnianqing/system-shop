package com.system.shop.service.impl;

import com.system.shop.base.ServiceImpl;
import com.system.shop.mapper.ProductSeasonMapper;
import com.system.shop.entity.ProductSeason;
import com.system.shop.service.ProductSeasonService;
import org.springframework.stereotype.Service;

@Service
public class ProductSeasonServiceImpl extends ServiceImpl<ProductSeasonMapper, ProductSeason> implements ProductSeasonService {


    @Override
    public Boolean saveOrUpdate(ProductSeason productSeason) {
        return null;
    }
}
