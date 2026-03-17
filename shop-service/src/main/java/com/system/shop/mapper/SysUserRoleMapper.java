package com.system.shop.mapper;

import com.system.shop.entity.SysUserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysUserRoleMapper {
    List<SysUserRole> findByUserId(@Param("userId") Long userId);
    
    void insert(SysUserRole userRole);
    
    void deleteByUserId(@Param("userId") Long userId);
    
    void deleteByRoleId(@Param("roleId") Long roleId);
} 