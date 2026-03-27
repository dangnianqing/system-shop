package com.system.shop.service.impl;

import com.system.shop.base.ServiceImpl;
import com.system.shop.enumer.RechargeType;
import com.system.shop.mapper.MemberIntegralHistoryMapper;
import com.system.shop.entity.Member;
import com.system.shop.entity.MemberIntegralHistory;
import com.system.shop.service.MemberIntegralHistoryService;
import com.system.shop.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberIntegralHistoryServiceImpl extends ServiceImpl<MemberIntegralHistoryMapper, MemberIntegralHistory> implements MemberIntegralHistoryService {

    @Autowired
    private MemberService memberService;

    @Override
    public Boolean saveIntegral(MemberIntegralHistory memberIntegralHistory) {
       /* Member member = memberService.selectById(memberIntegralHistory.getMemberId());
        Integer integer = member.getIntegral() == null ? 0 : member.getIntegral();
        memberIntegralHistory.setBefore(integer);
        Member bean = new Member();
        bean.setId(member.getId());
        if (memberIntegralHistory.getType().equals(RechargeType.recharge.name())) {
            bean.setIntegral((int) (member.getIntegral() + memberIntegralHistory.getIntegral()));
            memberIntegralHistory.setCurrent(bean.getIntegral());
        } else {
            bean.setIntegral((int) (member.getIntegral() - memberIntegralHistory.getIntegral()));
            memberIntegralHistory.setCurrent(bean.getIntegral());
        }
        memberService.updateByIdSelective(bean);*/
        return this.insert(memberIntegralHistory);
    }
}
