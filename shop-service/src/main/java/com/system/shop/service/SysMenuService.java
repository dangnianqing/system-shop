package com.system.shop.service;

import com.system.shop.entity.SysMenu;
import java.util.List;

public interface SysMenuService {
    List<SysMenu> findAll();
    SysMenu selectById(Long id);
    SysMenu create(SysMenu menu);
    SysMenu update(SysMenu menu);
    void delete(Long id);
} 