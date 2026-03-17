package com.system.shop.service;


import com.system.shop.base.IService;
import com.system.shop.entity.StoreLogistics;
import com.system.shop.search.LogisticsSearch;

import java.util.List;

public interface StoreLogisticsService extends IService<StoreLogistics> {


    Boolean saveOrUpdate(Long storeId, Long logisticsId, Integer status);

    List<StoreLogistics> selectList(Long storeId,LogisticsSearch search);
}
