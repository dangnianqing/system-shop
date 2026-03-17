package com.system.shop.mapper;

import com.system.shop.base.BaseMapper;
import com.system.shop.entity.StoreRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StoreRoleMapper extends BaseMapper<StoreRole> {

    Boolean deleteByIds(@Param("roleIds") List<Long> roleIds);
}