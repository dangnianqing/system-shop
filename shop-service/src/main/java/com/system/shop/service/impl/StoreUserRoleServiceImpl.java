package com.system.shop.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.system.shop.base.ServiceImpl;
import com.system.shop.mapper.StoreUserRoleMapper;
import com.system.shop.entity.StoreUserRole;
import com.system.shop.service.StoreUserRoleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StoreUserRoleServiceImpl extends ServiceImpl<StoreUserRoleMapper, StoreUserRole> implements StoreUserRoleService {


    @Override
    public List<Long> selectByUserId(Long userId) {
        List<StoreUserRole> list = baseMapper.selectByUserId(userId);
        return list.stream().map(StoreUserRole::getRoleId).collect(Collectors.toList());
    }

    @Override
    public Boolean insertUserRole(Long userId, List<Long> roleIds) {
        if (CollectionUtil.isNotEmpty(roleIds)) {
            this.deleteByUserId(userId);
            List<StoreUserRole> list = new ArrayList<>();
            roleIds.forEach(roleId -> {
                list.add(new StoreUserRole(userId, roleId));
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
}
