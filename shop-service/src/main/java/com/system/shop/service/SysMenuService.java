package com.system.shop.service;

import com.system.shop.base.IService;
import com.system.shop.entity.SysMenu;

import java.util.List;
public interface SysMenuService extends IService<SysMenu> {


    List<SysMenu>selectListTree();

    List<SysMenu>selectParentId(Long parentId);

    Boolean delete(Long id);

    SysMenu select(Long id);

    Boolean saveOrUpdate(SysMenu menu);
}
