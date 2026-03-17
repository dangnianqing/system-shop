package com.system.shop.mapper;

import com.system.shop.base.BaseMapper;
import com.system.shop.entity.StoreUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StoreUserMapper extends BaseMapper<StoreUser> {

    StoreUser selectByUserName(@Param("userName") String userName);

    Boolean deleteByIds(@Param("userIds") List<Long> userIds);
}