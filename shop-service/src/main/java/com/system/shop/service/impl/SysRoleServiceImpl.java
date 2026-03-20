package com.system.shop.service.impl;

import com.system.shop.base.ServiceImpl;
import com.system.shop.mapper.SysRoleMapper;
import com.system.shop.entity.SysRole;
import com.system.shop.entity.SysRoleMenu;
import com.system.shop.service.SysMenuService;
import com.system.shop.service.SysRoleMenuService;
import com.system.shop.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    @Autowired
    private SysRoleMenuService sysRoleMenuService;
    @Autowired
    private SysMenuService sysMenuService;

    @Override
    public Boolean update(SysRole role) {
        sysRoleMenuService.insetRoleMenu(role.getId(), role.getMenuIds());
        return updateByIdSelective(role);
    }


    @Override
    public Boolean delete(Long roleId) {
        sysRoleMenuService.deleteByRoleId(roleId);
        return this.deleteById(roleId);
    }

    @Override
    public Boolean deleteList(List<Long> roleIds) {
        sysRoleMenuService.deleteByRoleIds(roleIds);
        return baseMapper.deleteByIds(roleIds);
    }

    @Override
    public Boolean saveOrUpdate(SysRole role) {
        if (this.selectById(role.getId()) == null) {
            this.insert(role);
        } else {
            this.updateByIdSelective(role);
        }
        sysRoleMenuService.insetRoleMenu(role.getId(), role.getMenuIds());
        return true;
    }

    @Override
    public SysRole select(Long id) {
        SysRole sysRole = this.selectById(id);
        return sysRole;
    }

    @Override
    public Map<String, Object> selectMenuList(Long roleId) {
        return new HashMap<String, Object>() {{
            put("menuList", sysMenuService.selectListTree());
            put("roleMenuList", sysRoleMenuService.selectListByRoleId(roleId).stream().map(SysRoleMenu::getMenuId).toList());
        }};

    }

    @Override
    public Boolean saveRoleMenu(SysRole role) {
        sysRoleMenuService.insetRoleMenu(role.getId(), role.getMenuIds());
        return true;
    }
}
