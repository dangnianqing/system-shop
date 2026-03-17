package com.system.shop.mapper;

import com.system.shop.base.BaseMapper;
import com.system.shop.entity.MemberWx;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberWxMapper extends BaseMapper<MemberWx> {

    MemberWx selectByMiniAppOpenId(@Param("miniAppOpenId") String miniAppOpenId);
}