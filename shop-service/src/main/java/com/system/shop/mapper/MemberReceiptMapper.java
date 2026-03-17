package com.system.shop.mapper;

import com.system.shop.base.BaseMapper;
import com.system.shop.entity.MemberReceipt;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberReceiptMapper extends BaseMapper<MemberReceipt> {

    MemberReceipt selectByMemberId(@Param("memberId") Long memberId);
}