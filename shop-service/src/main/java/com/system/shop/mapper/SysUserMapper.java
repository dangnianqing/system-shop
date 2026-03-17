package com.system.shop.mapper;

import com.system.shop.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Optional;

@Mapper
public interface SysUserMapper {
    Optional<SysUser> findByUsername(@Param("username") String username);
    Optional<SysUser> findById(@Param("id") Long id);
    List<SysUser> findAll();
    void insert(SysUser user);
    void update(SysUser user);
    void delete(@Param("id") Long id);
} 