package com.system.shop.mapper;



import com.system.shop.base.BaseMapper;
import com.system.shop.entity.SysRoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {

    Boolean deleteByRoleId(@Param("roleId") Long roleId);

    Boolean deleteByRoleIds(@Param("roleIds") List<Long> roleIds);

    List<SysRoleMenu> selectListByRoleId(@Param("roleId")Long roleId);
}