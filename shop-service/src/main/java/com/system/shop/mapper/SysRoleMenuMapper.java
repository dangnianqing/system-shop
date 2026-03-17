package com.system.shop.mapper;

import com.system.shop.entity.SysRoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysRoleMenuMapper {
    List<SysRoleMenu> findByRoleId(@Param("roleId") Long roleId);
    
    void insert(SysRoleMenu roleMenu);
    
    void deleteByRoleId(@Param("roleId") Long roleId);
    
    void deleteByMenuId(@Param("menuId") Long menuId);
} 