package com.system.shop.service;

import com.system.shop.base.IService;
import com.system.shop.entity.ProductImage;

import java.util.List;

public interface ProductImageService  extends IService<ProductImage> {


    List<ProductImage> selectByProductId(Long productId);

    Boolean updateList(List<String> imagePath, Long productId);
}


