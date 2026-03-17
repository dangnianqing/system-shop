package com.system.shop.mapper;

import com.system.shop.base.BaseMapper;
import com.system.shop.entity.StoreLogistics;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface StoreLogisticsMapper extends BaseMapper<StoreLogistics> {

    StoreLogistics selectByStoreIdAndLogisticsId(@Param("storeId") Long storeId,@Param("logisticsId") Long logisticsId);
}