package com.system.shop.service;


import com.system.shop.base.IService;
import com.system.shop.entity.StoreMenu;
import com.system.shop.entity.StoreUser;

import java.util.List;

public interface StoreUserService extends IService<StoreUser> {


    StoreUser selectByUserName(String username);

    StoreUser select(Long userId);

    Boolean saveOrUpdate(StoreUser user);

    Boolean delete(Long userId);

    Boolean deleteList(List<Long> userIds);

    List<StoreMenu> selectMenusByUserId(Long userId);
}
