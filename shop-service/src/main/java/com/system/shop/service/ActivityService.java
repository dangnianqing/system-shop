package com.system.shop.service;

import com.system.shop.base.IService;
import com.system.shop.entity.Activity;

import java.util.List;
public interface ActivityService extends IService<Activity> {



    Boolean saveOrUpdate(Activity activity);

    Activity selectActivity(Long activityId, Long storeId);

    List<Activity> selectActivityByProductIdStoreId(String storeId, String productId);

    List<Activity> selectActivityByStoreId(Long storeId);
}
