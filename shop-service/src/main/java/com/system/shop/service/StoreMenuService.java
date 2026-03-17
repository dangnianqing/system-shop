package com.system.shop.service;


import com.system.shop.base.IService;
import com.system.shop.entity.StoreMenu;

import java.util.List;

public interface StoreMenuService extends IService<StoreMenu> {


    List<StoreMenu> selectListTree();

    Boolean delete(Long id);

    StoreMenu select(Long id);

    Boolean saveOrUpdate(StoreMenu menu);

    List<StoreMenu> selectByUserId(Long userId);
}
