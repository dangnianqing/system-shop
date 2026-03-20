package com.system.shop.service.impl;

import com.system.shop.base.ServiceImpl;
import com.system.shop.mapper.SysUserMapper;
import com.system.shop.entity.SysMenu;
import com.system.shop.entity.SysUser;
import com.system.shop.service.SysUserRoleService;
import com.system.shop.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    @Autowired
    private SysUserRoleService userRoleService;

    @Override
    public Boolean deleteList(List<Long> userIds) {
        userRoleService.deleteByUserIds(userIds);
        return baseMapper.deleteByIds(userIds);
    }

    @Override
    public Boolean delete(Long userId) {
        userRoleService.deleteByUserId(userId);
        return this.deleteById(userId);
    }

    @Override
    public SysUser select(Long id) {
        SysUser user = this.selectById(id);
        user.setRoleIds(userRoleService.selectByUserId(id));
        return user;
    }

    @Override
    public Boolean saveOrUpdate(SysUser user) {
        if (this.selectById(user.getId()) == null) {
            this.insert(user);
        } else {
            this.updateByIdSelective(user);
        }

        return userRoleService.insertUserRole(user.getId(), user.getRoleIds());
    }

    @Override
    public SysUser selectByUserName(String username) {
        Optional<SysUser> optional = this.baseMapper.selectByUserName(username);
        return optional.orElse(null);

    }

    @Override
    public List<SysMenu> selectMenusByUserId(Long userId) {
        return List.of();
    }
}
