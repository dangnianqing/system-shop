package com.system.shop.service.impl;

import com.system.shop.base.ServiceImpl;
import com.system.shop.exception.BusinessException;
import com.system.shop.common.ResultCode;
import com.system.shop.mapper.MemberMapper;
import com.system.shop.entity.Member;
import com.system.shop.service.MemberService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper,Member> implements MemberService {


    @Override
    public Member selectByUserName(String phone) {
        return baseMapper.selectByUserName(phone);
    }

    @Override
    public Boolean update(Member member, Long memberId) {
        Member bean=this.selectById(memberId);
        if (bean ==null){
            throw new BusinessException(ResultCode.MEMBER_NOT_EXIST);
        }
        member.setId(memberId);
        return baseMapper.updateByIdSelective(member);
    }

    @Override
    public boolean updateWalletPrice(Long memberId,BigDecimal wallet) {
        return baseMapper.updateWalletPrice(memberId,wallet);
    }
}
