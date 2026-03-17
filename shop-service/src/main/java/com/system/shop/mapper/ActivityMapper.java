package com.system.shop.mapper;

import com.system.shop.base.BaseMapper;
import com.system.shop.entity.Activity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ActivityMapper extends BaseMapper<Activity> {

    List<Activity> selectActivityByProductIdStoreId(@Param("storeId") String storeId, @Param("productId")String productId);

    List<Activity> selectActivityByStoreId(@Param("storeId")Long storeId);
}