package com.system.shop.service.impl;

import com.system.shop.base.ServiceImpl;
import com.system.shop.mapper.StoreRoleMapper;
import com.system.shop.entity.*;
import com.system.shop.entity.StoreRole;
import com.system.shop.service.StoreMenuService;
import com.system.shop.service.StoreRoleMenuService;
import com.system.shop.service.StoreRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StoreRoleServiceImpl extends ServiceImpl<StoreRoleMapper, StoreRole> implements StoreRoleService {

    @Autowired
    private StoreRoleMenuService storeRoleMenuService;
    @Autowired
    private StoreMenuService storeMenuService;

    @Override
    public Boolean delete(Long roleId) {
        storeRoleMenuService.deleteByRoleId(roleId);
        return this.deleteById(roleId);
    }

    @Override
    public Boolean deleteList(List<Long> roleIds) {
        storeRoleMenuService.deleteByRoleIds(roleIds);
        return baseMapper.deleteByIds(roleIds);
    }

    @Override
    public Boolean saveOrUpdate(StoreRole role) {
        if (this.selectById(role.getId()) == null) {
            this.insert(role);
        } else {
            this.updateByIdSelective(role);
        }
        storeRoleMenuService.insertRoleMenu(role.getId(), role.getMenuIds());
        return true;
    }


    @Override
    public Map<String, Object> selectMenuList(Long roleId) {
        return new HashMap<String, Object>() {{
            put("menuList", storeMenuService.selectListTree());
            put("roleMenuList", storeRoleMenuService.selectListByRoleId(roleId).stream().map(StoreRoleMenu::getMenuId).collect(Collectors.toList()));
        }};

    }

    @Override
    public Boolean saveRoleMenu(StoreRole role) {
        storeRoleMenuService.insertRoleMenu(role.getId(), role.getMenuIds());
        return true;
    }
}
