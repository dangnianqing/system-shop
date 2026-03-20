package com.system.shop.mapper;

import com.system.shop.base.BaseMapper;
import com.system.shop.entity.SysUserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    Boolean deleteByUserId(@Param("userId") Long userId);

    Boolean deleteByUserIds(@Param("userIds") List<Long> userId);

    List<Long> selectByUserId(@Param("userId")Long userId);
}