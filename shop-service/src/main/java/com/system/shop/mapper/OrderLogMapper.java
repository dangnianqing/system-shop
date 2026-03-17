package com.system.shop.mapper;

import com.system.shop.base.BaseMapper;
import com.system.shop.entity.OrderLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderLogMapper extends BaseMapper<OrderLog> {

    List<OrderLog> selectByOrderCode(@Param("orderCode") String orderCode);
}