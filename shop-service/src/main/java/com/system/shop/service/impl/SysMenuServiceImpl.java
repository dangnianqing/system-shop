package com.system.shop.service.impl;

import com.system.shop.entity.SysMenu;
import com.system.shop.mapper.SysMenuMapper;
import com.system.shop.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private SysMenuMapper menuMapper;

    @Override
    public List<SysMenu> findAll() {
        return menuMapper.findAll();
    }

    @Override
    public SysMenu selectById(Long id) {
        return menuMapper.selectById(id)
                .orElseThrow(() -> new RuntimeException("Menu not found with id: " + id));
    }

    @Override
    @Transactional
    public SysMenu create(SysMenu menu) {
        menu.setCreateTime(LocalDateTime.now());
        menu.setDeleteFlag(0);
        menuMapper.insert(menu);
        return menu;
    }

    @Override
    @Transactional
    public SysMenu update(SysMenu menu) {
        SysMenu existingMenu = selectById(menu.getId());
        menu.setUpdateTime(LocalDateTime.now());
        menuMapper.update(menu);
        return menu;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        SysMenu menu = selectById(id);
        menu.setDeleteFlag(1);
        menu.setUpdateTime(LocalDateTime.now());
        menuMapper.update(menu);
    }
} 