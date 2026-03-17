package com.system.shop.mapper;

import com.system.shop.base.BaseMapper;
import com.system.shop.entity.StoreMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StoreMenuMapper extends BaseMapper<StoreMenu> {

    List<StoreMenu> selectByUserId(@Param("userId") Long userId);
}