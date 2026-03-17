package com.system.shop.service;


import com.system.shop.base.IService;
import com.system.shop.entity.ProductCategory;

import java.util.List;

public interface ProductCategoryService extends IService<ProductCategory> {


    List<ProductCategory> selectTreeList();

    List<ProductCategory> selectListOneLevel();

    ProductCategory selectCategoryChildrenList(Long parentCode);
}

