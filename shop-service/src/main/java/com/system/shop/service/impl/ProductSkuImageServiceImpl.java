package com.system.shop.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.system.shop.base.ServiceImpl;
import com.system.shop.mapper.ProductSkuImageMapper;
import com.system.shop.entity.ProductSkuImage;
import com.system.shop.service.ProductSkuImageService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductSkuImageServiceImpl extends ServiceImpl<ProductSkuImageMapper, ProductSkuImage> implements ProductSkuImageService {


    @Override
    public List<ProductSkuImage> selectBySkuId(Long skuId) {
        return baseMapper.selectBySkuId(skuId);
    }

    @Override
    public boolean updateList(Long skuId, List<String> imagePath) {
        baseMapper.deleteBySkuId(skuId);
        if (CollectionUtil.isNotEmpty(imagePath)) {
            List<ProductSkuImage> list = new ArrayList<>();
            imagePath.forEach(item -> {
                ProductSkuImage productSkuImage = new ProductSkuImage();
                productSkuImage.setImagePath(item);
                productSkuImage.setSkuId(skuId);
                list.add(productSkuImage);
            });
            return this.batchInsert(list);
        }
        return true;
    }
}

