package com.system.shop.service;


import com.system.shop.base.IService;
import com.system.shop.entity.Member;
import com.system.shop.entity.OrderProductComment;
public interface OrderProductCommentService extends IService<OrderProductComment> {


    Boolean save(OrderProductComment orderProductComment, Member loginMember);


    OrderProductComment selectByOrderItemCode(String orderItemCode);
}
