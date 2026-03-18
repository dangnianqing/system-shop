package com.system.shop.service.impl;

import cn.hutool.json.JSONUtil;
import com.system.shop.base.ServiceImpl;
import com.system.shop.bean.cart.ProductSettlement;
import com.system.shop.bean.cart.ShoppSettlement;
import com.system.shop.bean.cart.StoreSettlement;
import com.system.shop.exception.BusinessException;
import com.system.shop.common.ResultCode;
import com.system.shop.mapper.OrderHeaderMapper;
import com.system.shop.entity.*;
import com.system.shop.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class OrderHeaderServiceImpl extends ServiceImpl<OrderHeaderMapper, OrderHeader> implements OrderHeaderService {

    @Autowired
    private MemberAddressService memberAddressService;
    @Autowired
    private ProductSkuService productSkuService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private OrderLogService orderLogService;

    @Override
    @Transactional
    public OrderHeader createOrderHeader(ShoppSettlement shoppSettlement) {
        Member member = memberService.selectById(shoppSettlement.getMemberId());
        OrderHeader orderHeader = new OrderHeader(shoppSettlement);
        //存放自订单/订单日志
        List<OrderLog> orderLogs = new ArrayList<>();
        List<Order> orders = new ArrayList<>();
        List<OrderItem> orderItems = new ArrayList<>();
        for (StoreSettlement storeSettlement : shoppSettlement.getStoreSettlements()) {
            log.info("创建订单 cartItem:{}", JSONUtil.toJsonStr(shoppSettlement));
            log.info("创建订单 storeCartItem:{}", JSONUtil.toJsonStr(storeSettlement));
            log.info("创建订单 orderHeader:{}", JSONUtil.toJsonStr(orderHeader));
            Order order = new Order(orderHeader.getOrderHeaderCode(), storeSettlement, member, shoppSettlement.getMemberAddress());
            for (ProductSettlement productSettlement : storeSettlement.getProductSettlements()) {
                //创建订单时直接扣库
                boolean status = productSkuService.updateReduceStock(productSettlement.getProductSkuId(), productSettlement.getNum());
                if (!status) {
                    throw new BusinessException(ResultCode.PRODUCT_INSUFFICIENT_STOCK, productSettlement.getProductName());
                }
                OrderItem orderItem = new OrderItem(order.getOrderCode(), productSettlement);
                orderItems.add(orderItem);

            }
            orders.add(order);
            //记录日志
            orderLogs.add(new OrderLog(order.getOrderCode(), "订单[" + order.getOrderCode() + "]创建", member.getId(), member.getUserName(), "会员"));
        }
        this.insert(orderHeader);
        orderService.batchInsert(orders);
        orderItemService.batchInsert(orderItems);
        orderLogService.batchInsert(orderLogs);
        //创建订单mq
        orders.forEach(order -> {
            orderService.sendMqMsg(order.getOrderCode(), order.getOrderStatus());
        });
        return orderHeader;
    }
}
