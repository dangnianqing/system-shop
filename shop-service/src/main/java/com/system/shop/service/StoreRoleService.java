package com.system.shop.service;

import com.system.shop.base.IService;
import com.system.shop.entity.StoreRole;

import java.util.List;
import java.util.Map;
public interface StoreRoleService  extends IService<StoreRole> {

    Boolean saveOrUpdate(StoreRole role);

    Boolean delete(Long id);

    Boolean deleteList(List<Long> roleIds);

    Map<String, Object> selectMenuList(Long roleId);

    Boolean saveRoleMenu(StoreRole role);

}
