package com.system.shop.service;

import com.system.shop.base.IService;
import com.system.shop.entity.SysMenu;
import com.system.shop.entity.SysUser;

import java.util.List;
import java.util.Optional;

public interface SysUserService extends IService<SysUser> {


    Boolean deleteList(List<Long> userIds);

    Boolean delete(Long userId);

    SysUser select(Long id);

    Boolean saveOrUpdate(SysUser user);

    SysUser selectByUserName(String username);

    List<SysMenu> selectMenusByUserId(Long userId);
}
