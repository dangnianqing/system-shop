package com.system.shop.service;

import com.system.shop.base.IService;
import com.system.shop.entity.SysUserRole;

import java.util.List;
public interface SysUserRoleService extends IService<SysUserRole> {


    Boolean insertUserRole(Long userId, List<Long> roleIds);

    Boolean deleteByUserId(Long userId);

    Boolean deleteByUserIds(List<Long> userIds);

    List<Long> selectByUserId(Long userId);
}
