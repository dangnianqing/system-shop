package com.system.shop.service;


import com.system.shop.base.IService;
import com.system.shop.entity.StoreUserRole;

import java.util.List;

public interface StoreUserRoleService extends IService<StoreUserRole> {

    List<Long> selectByUserId(Long userId);

    Boolean insertUserRole(Long userId, List<Long> roleIds);

    Boolean deleteByUserId(Long userId);

    Boolean deleteByUserIds(List<Long> userIds);
}
