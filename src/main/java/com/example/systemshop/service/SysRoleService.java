package com.example.systemshop.service;

import com.example.systemshop.entity.SysRole;
import java.util.List;

public interface SysRoleService {
    List<SysRole> findAll();
    SysRole findById(Long id);
    SysRole create(SysRole role);
    SysRole update(SysRole role);
    void delete(Long id);
    void assignMenus(Long roleId, List<Long> menuIds);
} 