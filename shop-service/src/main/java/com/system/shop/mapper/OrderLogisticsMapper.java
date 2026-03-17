package com.system.shop.mapper;

import com.system.shop.base.BaseMapper;
import com.system.shop.entity.OrderLogistics;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderLogisticsMapper extends BaseMapper<OrderLogistics> {

    List<OrderLogistics> selectOrderLogistics(@Param("orderCode") String orderCode);

    boolean deleteByOrderCode(@Param("orderCode")String orderCode);
}