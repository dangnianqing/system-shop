package com.system.shop.mapper;


import com.system.shop.base.BaseMapper;
import com.system.shop.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    List<SysMenu> selectParentId(@Param("parentId") Long parentId);

    boolean deleteByIds(@Param("removeIds") List<Long> removeIds);
}