package com.system.shop.mapper;

import com.system.shop.base.BaseMapper;
import com.system.shop.entity.StoreUserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StoreUserRoleMapper extends BaseMapper<StoreUserRole> {

    List<StoreUserRole> selectByUserId(@Param("userId") Long userId);

    Boolean deleteByUserIds(@Param("userIds") List<Long> userIds);

    Boolean deleteByUserId(@Param("userId") Long userId);
}