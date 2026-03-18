package com.system.shop.service.impl;

import com.system.shop.entity.SysRole;
import com.system.shop.mapper.SysRoleMapper;
import com.system.shop.mapper.SysRoleMenuMapper;
import com.system.shop.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleMapper roleMapper;

    @Autowired
    private SysRoleMenuMapper roleMenuMapper;

    @Override
    public List<SysRole> findAll() {
        return roleMapper.findAll();
    }

    @Override
    public SysRole selectById(Long id) {
        return roleMapper.selectById(id)
                .orElseThrow(() -> new RuntimeException("Role not found with id: " + id));
    }

    @Override
    @Transactional
    public SysRole create(SysRole role) {
        role.setCreateTime(LocalDateTime.now());
        role.setDeleteFlag(0);
        roleMapper.insert(role);
        return role;
    }

    @Override
    @Transactional
    public SysRole update(SysRole role) {
        SysRole existingRole = selectById(role.getId());
        role.setUpdateTime(LocalDateTime.now());
        roleMapper.update(role);
        return role;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        SysRole role = selectById(id);
        role.setDeleteFlag(1);
        role.setUpdateTime(LocalDateTime.now());
        roleMapper.update(role);
        // 删除角色菜单关联
        roleMenuMapper.deleteByRoleId(id);
    }

    @Override
    @Transactional
    public void assignMenus(Long roleId, List<Long> menuIds) {
        // 先删除原有菜单
        roleMenuMapper.deleteByRoleId(roleId);
        // 添加新菜单
//        for (Long menuId : menuIds) {
//            roleMenuMapper.insert(roleId, menuId);
//        }
    }
} 