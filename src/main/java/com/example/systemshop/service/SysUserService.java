package com.example.systemshop.service;

import com.example.systemshop.entity.SysUser;
import java.util.List;

public interface SysUserService {
    List<SysUser> findAll();
    SysUser findById(Long id);
    SysUser findByUsername(String username);
    SysUser create(SysUser user);
    SysUser update(SysUser user);
    void delete(Long id);
    void assignRoles(Long userId, List<Long> roleIds);
} 