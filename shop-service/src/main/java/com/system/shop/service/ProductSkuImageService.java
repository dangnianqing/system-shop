package com.system.shop.service;

import com.system.shop.base.IService;
import com.system.shop.entity.ProductSkuImage;

import java.util.List;

public interface ProductSkuImageService extends IService<ProductSkuImage> {

    List<ProductSkuImage> selectBySkuId(Long skuId);


    boolean updateList(Long skuId,List<String> imagePath);
}

