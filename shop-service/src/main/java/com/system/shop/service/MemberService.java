package com.system.shop.service;


import com.system.shop.base.IService;
import com.system.shop.entity.Member;

import java.math.BigDecimal;

public interface MemberService extends IService<Member> {

    Member selectByUserName(String phone);

    Boolean update(Member member, Long memberId);

    boolean updateWalletPrice( Long memberId,BigDecimal wallet);

}
