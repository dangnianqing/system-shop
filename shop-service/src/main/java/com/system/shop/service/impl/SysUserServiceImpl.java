package com.system.shop.service.impl;

import com.system.shop.entity.SysUser;
import com.system.shop.entity.SysUserRole;
import com.system.shop.exception.BusinessException;
import com.system.shop.mapper.SysUserMapper;
import com.system.shop.mapper.SysUserRoleMapper;
import com.system.shop.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    @Override
    public List<SysUser> findAll() {
        return userMapper.findAll();
    }

    @Override
    public SysUser findById(Long id) {
        return userMapper.findById(id)
                .orElseThrow(() -> new BusinessException("user.not.found"));
    }

    @Override
    public SysUser findByUsername(String username) {
        return userMapper.findByUsername(username)
                .orElseThrow(() -> new BusinessException("user.not.found"));
    }

    @Override
    @Transactional
    public SysUser create(SysUser user) {
        user.setCreateTime(LocalDateTime.now());
        user.setDeleteFlag(0);
        userMapper.insert(user);
        return user;
    }

    @Override
    @Transactional
    public SysUser update(SysUser user) {
        SysUser existingUser = findById(user.getId());
        user.setUpdateTime(LocalDateTime.now());
        userMapper.update(user);
        return user;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        SysUser user = findById(id);
        user.setDeleteFlag(1);
        user.setUpdateTime(LocalDateTime.now());
        userMapper.update(user);
        // 删除用户角色关联
        userRoleMapper.deleteByUserId(id);
    }

    @Override
    @Transactional
    public void assignRoles(Long userId, List<Long> roleIds) {
        // 先删除用户的所有角色
        userRoleMapper.deleteByUserId(userId);
        
        // 添加新的角色关联
        for (Long roleId : roleIds) {
            SysUserRole userRole = new SysUserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);
            userRoleMapper.insert(userRole);
        }
    }
} 