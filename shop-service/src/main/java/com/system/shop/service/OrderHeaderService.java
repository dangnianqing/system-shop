package com.system.shop.service;

import com.shop.bean.cart.ShoppSettlement;
import com.system.shop.base.IService;
import com.system.shop.entity.OrderHeader;
public interface OrderHeaderService extends IService<OrderHeader> {


    OrderHeader createOrderHeader(ShoppSettlement shoppSettlement);
}
