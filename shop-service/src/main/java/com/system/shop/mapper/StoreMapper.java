package com.system.shop.mapper;

import com.system.shop.base.BaseMapper;
import com.system.shop.entity.Store;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StoreMapper extends BaseMapper<Store> {

    List<Store> selectByIds(@Param("storeIds") List<Long> storeIds);
}