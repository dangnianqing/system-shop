package com.system.shop.service;

import com.system.shop.base.IService;
import com.system.shop.entity.ProductSpec;
import com.system.shop.entity.ProductSpecValue;

import java.util.List;
import java.util.Map;

public interface ProductSpecValueService extends IService<ProductSpecValue> {


    List<ProductSpecValue> selectBySpecIdAndProductId(Long productSpecId, Long productId);

    boolean updateList(List<ProductSpec> specList, Long productId);

    Map<Long, List<ProductSpecValue>> selectProductId(Long productId);
}
