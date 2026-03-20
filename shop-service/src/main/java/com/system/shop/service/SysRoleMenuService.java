package com.system.shop.service;

import com.system.shop.base.IService;
import com.system.shop.entity.SysRoleMenu;

import java.util.List;
public interface SysRoleMenuService extends IService<SysRoleMenu> {


    Boolean insetRoleMenu(Long id, List<Long> menuIds);

    Boolean deleteByRoleId(Long roleId);

    Boolean deleteByRoleIds(List<Long> roleIds);

    List<SysRoleMenu> selectListByRoleId(Long roleId);
}
