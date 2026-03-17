package com.system.shop.mapper;

import com.system.shop.base.BaseMapper;
import com.system.shop.entity.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

@Mapper
public interface MemberMapper extends BaseMapper<Member> {

    Member selectByUserName(@Param("userName") String phone);

    boolean updateWalletPrice(@Param("memberId")Long memberId,@Param("wallet") BigDecimal wallet);
}