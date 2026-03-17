package com.system.shop.service.service;

import com.system.shop.admin.entity.SysUser;
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