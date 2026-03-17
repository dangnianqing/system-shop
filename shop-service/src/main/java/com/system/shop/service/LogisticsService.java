package com.system.shop.service;

import com.system.shop.base.IService;
import com.system.shop.entity.Logistics;

import java.util.List;

public interface LogisticsService extends IService<Logistics> {

    Boolean deleteList(List<Long> ids);

    Boolean saveOrUpdate(Logistics logistics);

    List<Logistics> selectStoreList(Long storeId);
}
