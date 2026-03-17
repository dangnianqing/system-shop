package com.system.shop.service.impl;

import cn.hutool.core.text.CharSequenceUtil;
import com.system.shop.base.ServiceImpl;
import com.system.shop.enumer.AfterSaleStatus;
import com.system.shop.enumer.AfterSaleType;
import com.system.shop.enumer.OrderItemAfterSaleStatus;
import com.system.shop.mapper.OrderItemMapper;
import com.system.shop.entity.AfterSale;
import com.system.shop.entity.AfterSaleItem;
import com.system.shop.entity.OrderItem;
import com.system.shop.service.OrderItemService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem> implements OrderItemService {


    @Override
    public List<OrderItem> selectByOrderCode(String orderCode) {
        return baseMapper.selectByOrderCode(orderCode);
    }

    @Override
    public Boolean updateByOrderItemCodeSelective(OrderItem OrderItem) {
        return baseMapper.updateByOrderItemCodeSelective(OrderItem);
    }

    @Override
    public Boolean updateByOrderCodeSelective(OrderItem OrderItem) {
        return baseMapper.updateByOrderCodeSelective(OrderItem);
    }

    @Override
    public OrderItem selectByOrderItemCode(String orderItemCode) {
        return baseMapper.selectByOrderItemCode(orderItemCode);
    }

    @Override
    public Boolean updateByAfterSale(AfterSale afterSale, List<AfterSaleItem> afterSaleItems) {
        for (AfterSaleItem afterSaleItem : afterSaleItems) {
            OrderItem bean = new OrderItem();
            //根据商品skuId及订单sn获取子订单
            OrderItem orderItem = this.selectByOrderItemCode(afterSaleItem.getOrderItemCode());
            if (CharSequenceUtil.equalsAny(afterSale.getOrderAfterStatus(), AfterSaleStatus.BUYER_CANCEL.name(), AfterSaleStatus.SELLER_TERMINATION.name())) {
                bean.setAfterSalePrice(orderItem.getAfterSalePrice().subtract(afterSaleItem.getAfterSalePrice()));
                bean.setAfterSaleNum(orderItem.getAfterSaleNum() - afterSaleItem.getAfterSaleNum());
            } else if (afterSale.getOrderAfterStatus().equals(AfterSaleStatus.COMPLETE.name()) && afterSale.getOrderAfterType().equals(AfterSaleType.REFUND.name())) {
                bean.setAfterSalePrice(orderItem.getAfterSalePrice().add(afterSaleItem.getAfterSalePrice()));
                bean.setAfterSaleNum(orderItem.getAfterSaleNum() + afterSaleItem.getAfterSaleNum());
            } else if (afterSale.getOrderAfterStatus().equals(AfterSaleStatus.BUYER_RETURN.name()) && afterSale.getOrderAfterType().equals(AfterSaleType.RETURN_REFUND.name())) {
                bean.setAfterSalePrice(orderItem.getAfterSalePrice().add(afterSaleItem.getAfterSalePrice()));
                bean.setAfterSaleNum(orderItem.getAfterSaleNum() + afterSaleItem.getAfterSaleNum());
            }
            if (orderItem.getAfterSaleStatus().equals(OrderItemAfterSaleStatus.PART_AFTER_SALE.name())
                    || orderItem.getAfterSaleStatus().equals(OrderItemAfterSaleStatus.NOT_APPLIED.name())
                    || orderItem.getAfterSaleStatus().equals(OrderItemAfterSaleStatus.ALREADY_APPLIED.name())) {
                if (bean.getAfterSaleNum() == null || bean.getAfterSaleNum() == 0) {
                    //修改订单的售后状态--未申请
                    bean.setAfterSaleStatus(OrderItemAfterSaleStatus.NOT_APPLIED.name());
                } else if (bean.getAfterSaleNum().equals(orderItem.getProductNum())) {
                    //修改订单的售后状态--已申请
                    bean.setAfterSaleStatus(OrderItemAfterSaleStatus.ALREADY_APPLIED.name());
                } else if (0 < bean.getAfterSaleNum() && bean.getAfterSaleNum() < orderItem.getProductNum()) {
                    //修改订单的售后状态--部分售后
                    bean.setAfterSaleStatus(OrderItemAfterSaleStatus.PART_AFTER_SALE.name());
                }
            }
            bean.setOrderItemCode(orderItem.getOrderItemCode());
            //修改orderItem订单
            this.updateByOrderItemCodeSelective(bean);
        }
        return true;
    }
}
