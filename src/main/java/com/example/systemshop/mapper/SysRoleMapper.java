package com.example.systemshop.mapper;

import com.example.systemshop.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Optional;

@Mapper
public interface SysRoleMapper {
    Optional<SysRole> findById(@Param("id") Long id);
    List<SysRole> findAll();
    void insert(SysRole role);
    void update(SysRole role);
} 