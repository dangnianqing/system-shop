package com.system.shop.service.impl;

import com.system.shop.base.ServiceImpl;
import com.system.shop.enumer.CommentStatus;
import com.system.shop.mapper.OrderMapper;
import com.system.shop.mapper.OrderProductCommentMapper;
import com.system.shop.entity.Member;
import com.system.shop.entity.Order;
import com.system.shop.entity.OrderItem;
import com.system.shop.entity.OrderProductComment;
import com.system.shop.service.OrderItemService;
import com.system.shop.service.OrderProductCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class OrderProductCommentServiceImpl extends ServiceImpl<OrderProductCommentMapper, OrderProductComment> implements OrderProductCommentService {
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public Boolean save(OrderProductComment orderProductComment, Member loginMember) {
        orderProductComment.setMemberId(loginMember.getId());
        orderProductComment.setMemberName(loginMember.getUserName());
        orderProductComment.setMemberImage(loginMember.getFace());
        orderProductComment.setCommentTime(new Date());
        Order order = orderMapper.selectByOrderCode(orderProductComment.getOrderCode());
        orderProductComment.setStoreId(order.getStoreId());
        orderProductComment.setStoreName(order.getStoreName());
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderItemCode(orderProductComment.getOrderItemCode());
        orderItem.setCommentStatus(CommentStatus.FINISHED.name());
        orderItemService.updateByOrderItemCodeSelective(orderItem);
        return this.insert(orderProductComment);
    }

    @Override
    public OrderProductComment selectByOrderItemCode(String orderItemCode) {
        return baseMapper.selectByOrderItemCode(orderItemCode);
    }


}
