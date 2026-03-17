package com.system.shop.mapper;

import com.system.shop.base.BaseMapper;
import com.system.shop.entity.ActivityProduct;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ActivityProductMapper extends BaseMapper<ActivityProduct> {

    boolean deleteByActivityId(@Param("activityId") Long activityId);

    List<ActivityProduct> selectProductList(@Param("activityId")Long activityId,@Param("storeId")Long storeId);

    List<Long> selectProductIds(@Param("activityId")Long activityId);
}