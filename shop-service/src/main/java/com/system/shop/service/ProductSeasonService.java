package com.system.shop.service;

import com.system.shop.base.IService;
import com.system.shop.entity.ProductSeason;
public interface ProductSeasonService extends IService<ProductSeason> {


    Boolean saveOrUpdate(ProductSeason productSeason);
}
