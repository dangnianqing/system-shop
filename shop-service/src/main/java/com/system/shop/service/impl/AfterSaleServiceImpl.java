package com.system.shop.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.system.shop.base.ServiceImpl;
import com.system.shop.bean.Operator;
import com.system.shop.bean.order.AfterSaleInfo;
import com.system.shop.bean.search.AfterSaleSearch;
import com.system.shop.enumer.AfterSaleRefundStatus;
import com.system.shop.enumer.AfterSaleStatus;
import com.system.shop.enumer.AfterSaleType;
import com.system.shop.enumer.OrderItemAfterSaleStatus;
import com.system.shop.enumer.OrderStatus;
import com.system.shop.exception.BusinessException;
import com.system.shop.common.ResultCode;
import com.system.shop.mapper.AfterSaleMapper;
import com.system.shop.entity.AfterSale;
import com.system.shop.entity.AfterSaleItem;
import com.system.shop.entity.AfterSaleLog;
import com.system.shop.entity.Order;
import com.system.shop.entity.OrderItem;
import com.system.shop.service.AfterSaleItemService;
import com.system.shop.service.AfterSaleLogService;
import com.system.shop.service.AfterSaleService;
import com.system.shop.service.OrderItemService;
import com.system.shop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AfterSaleServiceImpl extends ServiceImpl<AfterSaleMapper, AfterSale> implements AfterSaleService {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private AfterSaleItemService afterSaleItemService;
    @Autowired
    private AfterSaleLogService afterSaleLogService;

    @Override
    public AfterSale selectAfterSaleInfo(String orderCode) {
        //获取售后类型
        Order order = orderService.selectByOrderCode(orderCode);
        //订单未支付，不能申请申请售后
        if (!order.getOrderStatus().equals(OrderStatus.COMPLETE.name()) && !order.getOrderStatus().equals(OrderStatus.PENDING_RECEIPT.name())) {
            throw new BusinessException(ResultCode.AFTER_SALES_ORDER_STATUS_ERROR);
        }
        List<OrderItem> orderItems = order.getOrderItems();
        List<AfterSaleItem> afterSaleItems = new ArrayList<>();
        for (OrderItem orderItem : orderItems) {
            //未申请售后订单货物或部分售后才能进行申请
            if (orderItem.getAfterSaleStatus().equals(OrderItemAfterSaleStatus.NOT_APPLIED.name()) || orderItem.getAfterSaleStatus().equals(OrderItemAfterSaleStatus.PART_AFTER_SALE.name())) {
                afterSaleItems.add(new AfterSaleItem(orderItem));
            }
        }
        if (CollectionUtil.isEmpty(afterSaleItems)) {
            throw new BusinessException(ResultCode.AFTER_SALES_ORDER_STATUS_ERROR);
        }
        AfterSale bean = new AfterSale(order, afterSaleItems);
        return bean;
    }

    @Override
    @Transactional
    public Boolean saveAfterSale(AfterSaleInfo afterSaleInfo, Operator operator) {
        Order order = orderService.selectByOrderCode(afterSaleInfo.getOrderCode());
        AfterSale afterSale = new AfterSale(order, afterSaleInfo);
        List<OrderItem> orderItems = new ArrayList<>();
        List<AfterSaleItem> afterSaleItems = new ArrayList<>();
        afterSaleInfo.getAfterSaleItemInfos().forEach(afterSaleItemInfo -> {
            //获取订单货物判断是否可申请售后
            OrderItem orderItem = orderItemService.selectByOrderItemCode(afterSaleItemInfo.getOrderItemCode());
            //未申请售后或部分售后订单货物才能进行申请
            if (orderItem.getAfterSaleStatus().equals(OrderItemAfterSaleStatus.NEW.name()) || orderItem.getAfterSaleStatus().equals(OrderItemAfterSaleStatus.EXPIRED.name()) || orderItem.getAfterSaleStatus().equals(OrderItemAfterSaleStatus.ALREADY_APPLIED.name())) {
                throw new BusinessException(ResultCode.AFTER_SALES_ITEM_STATUS_ERROR);
            }
            //申请商品数量不能超过商品总数量-售后商品数量
            if (afterSaleItemInfo.getAfterNum() > (orderItem.getProductNum() - orderItem.getAfterSaleNum())) {
                throw new BusinessException(ResultCode.AFTER_SALES_QUANTITY_ERROR);
            }
            AfterSaleItem afterSaleItem = new AfterSaleItem(afterSale.getAfterSaleCode(), orderItem, afterSaleItemInfo);
            afterSaleItems.add(afterSaleItem);

            afterSale.setApplyAfterSalePrice(afterSale.getAfterSalePrice() == null ? afterSaleItem.getAfterSalePrice() : afterSale.getAfterSalePrice().add(afterSaleItem.getAfterSalePrice()));
            afterSale.setAfterSalePrice(afterSale.getApplyAfterSalePrice());
            afterSale.setAfterSaleNum(afterSale.getAfterSaleNum() == null ? afterSaleItem.getAfterSaleNum() : afterSale.getAfterSaleNum() + afterSaleItem.getAfterSaleNum());
            OrderItem newOrderItem = new OrderItem(orderItem.getOrderItemCode(), OrderItemAfterSaleStatus.ALREADY_APPLIED.name());
            orderItems.add(newOrderItem);
        });
        AfterSaleLog afterSaleLog = new AfterSaleLog(afterSale.getAfterSaleCode(), "售后申请:售后编号:[" + afterSale.getAfterSaleCode() + "]", operator.getOperatorId(), operator.getOperatorName(), operator.getOperatorType());
        afterSaleLogService.insert(afterSaleLog);
        this.insert(afterSale);
        afterSaleItemService.batchInsert(afterSaleItems);
        orderItems.forEach(orderItem -> {
            orderItemService.updateByOrderItemCodeSelective(orderItem);
        });
        return true;
    }

    @Override
    public PageInfo<AfterSale> findPage(AfterSaleSearch afterSaleSearch) {
        PageHelper.startPage(afterSaleSearch.getPageNum(), afterSaleSearch.getPageSize());
        return new PageInfo(baseMapper.findPage(afterSaleSearch.querymap()));
    }

    @Override
    public AfterSale selectByAfterSaleCode(String afterSaleCode) {
        return baseMapper.selectByAfterSaleCode(afterSaleCode);
    }

    @Override
    public AfterSale selectAfterSaleCode(String afterSaleCode) {
        AfterSale afterSale = this.selectByAfterSaleCode(afterSaleCode);
        List<AfterSaleItem> afterSaleItems = afterSaleItemService.selectByAfterSaleCode(afterSaleCode);
        afterSale.setAfterSaleItems(afterSaleItems);
        return afterSale;
    }

    @Override
    public Boolean auditAfterSale(String afterSaleCode, Integer status, BigDecimal afterSalePrice, String storeRemark) {
        //根据售后单号获取售后单
        AfterSale afterSale = this.selectByAfterSaleCode(afterSaleCode);
        //判断为待审核的售后服务
        if (!afterSale.getOrderAfterStatus().equals(AfterSaleStatus.APPLY.name())) {
            throw new BusinessException(ResultCode.AFTER_SALES_ORDER_STATUS_ERROR);
        }
        //判断退款金额与付款金额是否正确,退款金额不能大于付款金额
        if (afterSalePrice.compareTo(afterSale.getApplyAfterSalePrice()) > 0) {
            throw new BusinessException(ResultCode.AFTER_SALES_REFUND_AMOUNT_ERROR);
        }
        List<AfterSaleItem> afterSaleItems = afterSaleItemService.selectByAfterSaleCode(afterSaleCode);
        if (status == 1) {
            //如果修改了退款金额则重新均摊退款金额
            if (afterSalePrice.compareTo(afterSale.getApplyAfterSalePrice()) < 0) {
                afterSale.setAfterSalePrice(afterSalePrice);
                BigDecimal price = BigDecimal.ZERO;
                for (int a = 0; a < afterSaleItems.size(); a++) {
                    AfterSaleItem afterSaleItem = afterSaleItems.get(a);
                    BigDecimal itemAfterSalePrice = afterSale.getAfterSalePrice().divide(afterSale.getApplyAfterSalePrice()).multiply(afterSaleItem.getAfterSalePrice()).setScale(2, RoundingMode.DOWN);
                    if (a == afterSaleItems.size() - 1) {
                        afterSaleItem.setAfterSalePrice(afterSale.getAfterSalePrice().subtract(price));
                    } else {
                        afterSaleItem.setAfterSalePrice(itemAfterSalePrice);
                        price = price.add(afterSaleItem.getAfterSalePrice());
                    }
                }
                afterSaleItemService.updateBatchSelective(afterSaleItems);
            }
            if (afterSale.getOrderAfterType().equals(AfterSaleType.REFUND.name())) {
                afterSale.setOrderAfterStatus(AfterSaleStatus.PASS.name());
                //钱款原路退回
                if (this.refund(afterSale)) {
                    afterSale.setRefundTime(new Date());
                    afterSale.setRefundStatus(AfterSaleRefundStatus.REFUND_SUCCESS.name());
                    afterSale.setOrderAfterStatus(AfterSaleStatus.COMPLETE.name());
                } else {
                    throw new BusinessException(ResultCode.AFTER_SALES_REFUND_FAILED);
                }
            }
            if (afterSale.getOrderAfterType().equals(AfterSaleType.RETURN_REFUND.name())) {
                afterSale.setOrderAfterStatus(AfterSaleStatus.BUYER_RETURN.name());
            }
        } else {
            afterSale.setOrderAfterStatus(AfterSaleStatus.REFUSE.name());
        }
        afterSale.setStoreRemark(storeRemark);
        //修改订单明细行售后数据
        orderItemService.updateByAfterSale(afterSale, afterSaleItems);
        return this.updateByIdSelective(afterSale);
    }

    @Override
    public Boolean refund(AfterSale afterSale) {
        return true;
    }


    public static void main(String[] args) {
        BigDecimal applyAfterSalePrice = new BigDecimal("100");
        BigDecimal afterSalePrice = new BigDecimal("110");
        System.out.println(afterSalePrice.compareTo(applyAfterSalePrice));
    }


}
