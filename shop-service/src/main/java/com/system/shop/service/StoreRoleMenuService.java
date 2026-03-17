package com.system.shop.service;

import com.system.shop.base.IService;
import com.system.shop.entity.StoreRoleMenu;

import java.util.List;
public interface StoreRoleMenuService extends IService<StoreRoleMenu> {

    Boolean deleteByRoleId(Long roleId);

    Boolean deleteByRoleIds(List<Long> roleIds);

    Boolean insertRoleMenu(Long roleId, List<Long> menuIds);

    List<StoreRoleMenu> selectListByRoleId(Long roleId);
}
