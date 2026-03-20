package com.system.shop.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.system.shop.base.ServiceImpl;
import com.system.shop.mapper.SysUserRoleMapper;
import com.system.shop.entity.SysUserRole;
import com.system.shop.service.SysUserRoleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

    @Override
    public Boolean insertUserRole(Long userId, List<Long> roleIds) {
        List<SysUserRole> list = new ArrayList<>();
        this.deleteByUserId(userId);
        if (CollectionUtil.isNotEmpty(roleIds)) {
            roleIds.forEach(roleId -> {
                SysUserRole userRole = new SysUserRole(userId, roleId);
                list.add(userRole);
            });
            return batchInsert(list);
        }
        return true;

    }

    @Override
    public Boolean deleteByUserId(Long userId) {
        return baseMapper.deleteByUserId(userId);
    }

    @Override
    public Boolean deleteByUserIds(List<Long> userIds) {
        return baseMapper.deleteByUserIds(userIds);
    }

    @Override
    public List<Long> selectByUserId(Long userId) {
        return baseMapper.selectByUserId(userId);
    }

}
