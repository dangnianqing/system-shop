package com.system.shop.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.system.shop.base.ServiceImpl;
import com.system.shop.mapper.StoreRoleMenuMapper;
import com.system.shop.entity.StoreRoleMenu;
import com.system.shop.service.StoreRoleMenuService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StoreRoleMenuServiceImpl extends ServiceImpl<StoreRoleMenuMapper, StoreRoleMenu> implements StoreRoleMenuService {


    @Override
    public Boolean deleteByRoleId(Long roleId) {
        return baseMapper.deleteByRoleId(roleId);
    }

    @Override
    public Boolean deleteByRoleIds(List<Long> roleIds) {
        return baseMapper.deleteByRoleIds(roleIds);
    }

    @Override
    public Boolean insertRoleMenu(Long roleId, List<Long> menuIds) {
        if (CollectionUtil.isNotEmpty(menuIds)){
            this.deleteByRoleId(roleId);
            List<StoreRoleMenu> list = new ArrayList<>();
            menuIds.forEach(menuId -> {
                list.add(new StoreRoleMenu(roleId, menuId));
            });
            return this.batchInsert(list);
        }
        return true;
    }

    @Override
    public List<StoreRoleMenu> selectListByRoleId(Long roleId) {

        return baseMapper.selectListByRoleId(roleId);
    }
}
