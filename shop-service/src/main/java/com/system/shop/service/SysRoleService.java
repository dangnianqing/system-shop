package com.system.shop.service;

import com.system.shop.base.IService;
import com.system.shop.entity.SysRole;

import java.util.List;
import java.util.Map;

public interface SysRoleService extends IService<SysRole> {


    Boolean update(SysRole user);

    Boolean delete(Long roleId);

    Boolean deleteList(List<Long> roleIds);

    Boolean saveOrUpdate(SysRole role);

    SysRole select(Long id);

    Map<String ,Object> selectMenuList(Long roleId);

    Boolean saveRoleMenu(SysRole role);
}
