package com.system.shop.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.system.shop.base.ServiceImpl;
import com.system.shop.mapper.ProductImageMapper;
import com.system.shop.entity.ProductImage;
import com.system.shop.service.ProductImageService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductImageServiceImpl extends ServiceImpl<ProductImageMapper, ProductImage> implements ProductImageService {


    @Override
    public List<ProductImage> selectByProductId(Long productId) {
        return baseMapper.selectByProductId(productId);
    }

    @Override
    public Boolean updateList(List<String> imagePath, Long productId) {
        baseMapper.deleteByProductId( productId);
        if (CollectionUtil.isNotEmpty(imagePath)) {
            List<ProductImage> list = new ArrayList<>();
            imagePath.forEach(image -> {
                ProductImage productImage = new ProductImage();
                productImage.setProductId(productId);
                productImage.setImagePath(image);
                list.add(productImage);
            });
            return this.batchInsert(list);
        }
        return true;
    }
}

