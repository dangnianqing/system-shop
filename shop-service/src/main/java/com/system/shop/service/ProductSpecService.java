package com.system.shop.service;


import com.system.shop.base.IService;
import com.system.shop.entity.ProductSpec;

public interface ProductSpecService extends IService<ProductSpec> {


    Boolean saveOrUpdate(ProductSpec productSpec);

}
