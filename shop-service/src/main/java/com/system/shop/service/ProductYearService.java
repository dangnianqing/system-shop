package com.system.shop.service;

import com.system.shop.base.IService;
import com.system.shop.entity.ProductYear;
public interface ProductYearService extends IService<ProductYear> {


    Boolean saveOrUpdate(ProductYear productYear);
}
