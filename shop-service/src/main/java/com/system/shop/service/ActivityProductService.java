package com.system.shop.service;

import com.system.shop.base.IService;
import com.system.shop.entity.ActivityProduct;

import java.util.List;

public interface ActivityProductService extends IService<ActivityProduct> {


    Boolean updateByActivityId(Long activityId, List<Long> productIds);

    List<ActivityProduct> selectProductList(Long activityId,Long storeId);


    List<Long> selectProductIds(Long activityId);
}

