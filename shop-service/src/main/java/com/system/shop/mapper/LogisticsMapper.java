package com.system.shop.mapper;

import com.system.shop.base.BaseMapper;
import com.system.shop.entity.Logistics;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LogisticsMapper extends BaseMapper<Logistics> {

    boolean deleteList(@Param("ids") List<Long> ids);

    List<Logistics> selectStoreList(@Param("storeId")Long storeId);
}