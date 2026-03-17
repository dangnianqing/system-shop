package com.system.shop.mapper;

import com.system.shop.base.BaseMapper;
import com.system.shop.entity.StoreRoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StoreRoleMenuMapper extends BaseMapper<StoreRoleMenu> {

    Boolean deleteByRoleIds(@Param("roleIds") List<Long> roleIds);

    Boolean deleteByRoleId(@Param("roleId")Long roleId);

    List<StoreRoleMenu> selectListByRoleId(@Param("roleId")Long roleId);
}