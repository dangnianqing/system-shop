package com.system.shop.mapper;

import com.system.shop.base.BaseMapper;
import com.system.shop.entity.StoreDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface StoreDetailMapper extends BaseMapper<StoreDetail> {

    StoreDetail selectByStoreId(@Param("storeId") Long storeId);
}