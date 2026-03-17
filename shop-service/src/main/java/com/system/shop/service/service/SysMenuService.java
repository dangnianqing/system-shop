package com.system.shop.service.service;

import com.system.shop.admin.entity.SysMenu;
import java.util.List;

public interface SysMenuService {
    List<SysMenu> findAll();
    SysMenu findById(Long id);
    SysMenu create(SysMenu menu);
    SysMenu update(SysMenu menu);
    void delete(Long id);
} 