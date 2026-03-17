package com.system.shop.mapper;

import com.system.shop.base.BaseMapper;
import com.system.shop.bean.order.OrderTagInfo;
import com.system.shop.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {

    Order selectByOrderCode(@Param("orderCode") String orderCode);

    List<OrderTagInfo> selectOrderTagInfo(@Param("memberId") Long memberId);

    List<Order> findPage(@Param("param") Map<String, Object> param);
}