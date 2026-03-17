package com.system.shop.service.mapper;

import com.system.shop.admin.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface SysMenuMapper {
    Optional<SysMenu> findById(@Param("id") Long id);
    List<SysMenu> findAll();
    void insert(SysMenu menu);
    void update(SysMenu menu);
} 