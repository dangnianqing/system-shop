package com.system.shop.service;


import com.system.shop.base.IService;
import com.system.shop.entity.AfterSale;
import com.system.shop.entity.AfterSaleItem;
import com.system.shop.entity.OrderItem;

import java.util.List;

public interface OrderItemService extends IService<OrderItem> {


    List<OrderItem> selectByOrderCode(String orderCode);

    Boolean updateByOrderItemCodeSelective(OrderItem OrderItem);

    Boolean updateByOrderCodeSelective(OrderItem OrderItem);

    OrderItem selectByOrderItemCode(String orderItemCode);

    Boolean updateByAfterSale(AfterSale afterSale, List<AfterSaleItem> afterSaleItems);
}
