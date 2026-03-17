package com.system.shop.service;


import com.system.shop.base.IService;
import com.system.shop.entity.Store;

import java.util.List;

public interface StoreService extends IService<Store> {


    List<Store> selectByIds(List<Long> storeIds);
}

