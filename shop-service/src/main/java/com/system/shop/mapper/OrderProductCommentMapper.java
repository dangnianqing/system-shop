package com.system.shop.mapper;

import com.system.shop.base.BaseMapper;
import com.system.shop.entity.OrderProductComment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderProductCommentMapper extends BaseMapper<OrderProductComment> {

    OrderProductComment selectByOrderItemCode(@Param("orderItemCode") String orderItemCode);
}