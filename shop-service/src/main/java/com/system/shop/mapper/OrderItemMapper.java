package com.system.shop.mapper;

import com.system.shop.base.BaseMapper;
import com.system.shop.entity.OrderItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderItemMapper extends BaseMapper<OrderItem> {

    List<OrderItem> selectByOrderCode(@Param("orderCode") String orderCode);

    Boolean updateByOrderItemCodeSelective(OrderItem orderItem);

    Boolean updateByOrderCodeSelective(OrderItem orderItem);

    OrderItem selectByOrderItemCode(@Param("orderItemCode") String orderItemCode);
}