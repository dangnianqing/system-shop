package com.system.shop.mapper;

import com.system.shop.base.BaseMapper;
import com.system.shop.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    Boolean deleteByIds(@Param("roleIds") List<Long> roleIds);
}