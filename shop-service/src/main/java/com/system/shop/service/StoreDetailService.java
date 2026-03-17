package com.system.shop.service;

import com.system.shop.base.IService;
import com.system.shop.entity.StoreDetail;

public interface StoreDetailService extends IService<StoreDetail> {


    StoreDetail selectByStoreId(Long storeId);

    Boolean saveOrUpdate(StoreDetail storeDetail);
}
