package com.system.shop.service;

import com.system.shop.base.IService;
import com.system.shop.entity.OrderLogistics;

import java.util.List;

public interface OrderLogisticsService extends IService<OrderLogistics> {


    List<OrderLogistics> selectOrderLogistics(String orderCode);

    Boolean update(String orderCode, List<OrderLogistics> orderLogisticsList);

    OrderLogistics selectLogisticsId(Long orderLogisticsId);
}
