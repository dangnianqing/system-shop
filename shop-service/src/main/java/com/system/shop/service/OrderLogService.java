package com.system.shop.service;

import com.system.shop.base.IService;
import com.system.shop.entity.OrderLog;

import java.util.List;
public interface OrderLogService extends IService<OrderLog> {


    List<OrderLog> selectByOrderCode(String orderCode);
}
