package com.system.shop.mapper;

import com.system.shop.base.BaseMapper;
import com.system.shop.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {


    Boolean deleteByIds(@Param("userIds") List<Long> userIds);

    Optional<SysUser> selectByUserName(@Param("username") String username);
}