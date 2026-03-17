package com.system.shop.service;

import com.system.shop.base.IService;
import com.system.shop.entity.ProductSku;
import com.system.shop.entity.ProductSpec;

import java.util.List;

public interface ProductSkuService extends IService<ProductSku> {

    List<ProductSku> selectByProductId(Long productId);

    List<ProductSku> createSkuList(Long productId, String productCode, String productName, List<ProductSpec> specList);

    boolean updateList(List<ProductSku> skuList, Long productId);

    boolean updateReduceStock(Long productSkuId, Integer num);

    boolean updateIncreaseStock(Long productSkuId, Integer num);


}

