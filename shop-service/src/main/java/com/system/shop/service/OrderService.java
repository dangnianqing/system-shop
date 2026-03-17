package com.system.shop.service;

import com.github.pagehelper.PageInfo;
import com.system.shop.base.IService;
import com.system.shop.bean.Operator;
import com.system.shop.bean.order.OrderTagInfo;
import com.system.shop.bean.search.OrderSearch;
import com.system.shop.entity.Order;

import java.util.List;

public interface OrderService extends IService<Order> {


    PageInfo<Order> findPage(OrderSearch orderSearch);

    Order selectByOrderCode(String orderCode);

    Order selectOrderCode(String orderCode);

    Boolean updateOrderCancel(String orderCode, Integer cancel);

    Boolean updateAddress(Order order);


    Boolean updatePayPrice(Order order,Operator operator);

    List<OrderTagInfo> selectOrderTagInfo(Long memberId);

    Boolean saveOrderLogistics(Order order, Operator operator);

    Boolean updateCompleteOrder(String orderCode,Operator operator);

    Boolean updateCancelOrder(String orderCode, Operator operator);

    Boolean updatePayOrder(String orderCode, Operator operator);

    Boolean sendMqMsg(String orderCode, String orderStatus);

}
