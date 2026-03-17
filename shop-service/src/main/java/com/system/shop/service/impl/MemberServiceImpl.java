package com.system.shop.service.impl;

import com.system.shop.base.ServiceImpl;
import com.system.shop.exception.BaseException;
import com.system.shop.result.ResultCode;
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
        Member bean=baseMapper.selectById(memberId);
        if (bean ==null){
            throw new BaseException(ResultCode.PARAM_ERROR,"会员不存在");
        }
        member.setId(memberId);
        return baseMapper.updateByIdSelective(member);
    }

    @Override
    public boolean updateWalletPrice(Long memberId,BigDecimal wallet) {
        return baseMapper.updateWalletPrice(memberId,wallet);
    }
}
