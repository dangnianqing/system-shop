package com.system.shop.service.impl;

import com.system.shop.base.ServiceImpl;
import com.system.shop.enumer.RechargeType;
import com.system.shop.mapper.MemberWalletHistoryMapper;
import com.system.shop.entity.Member;
import com.system.shop.entity.MemberWalletHistory;
import com.system.shop.service.MemberService;
import com.system.shop.service.MemberWalletHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class MemberWalletHistoryServiceImpl extends ServiceImpl<MemberWalletHistoryMapper, MemberWalletHistory> implements MemberWalletHistoryService {
    @Autowired
    private MemberService memberService;

    @Override
    public Boolean saveWallet(MemberWalletHistory memberWalletHistory) {

       /* Member member = memberService.selectById(memberWalletHistory.getMemberId());
        BigDecimal wallet = member.getWallet()==null?new BigDecimal("0.00"):member.getWallet();
        memberWalletHistory.setBefore(wallet);
        memberWalletHistory.setWallet(new BigDecimal(memberWalletHistory.getValue()));

        Member bean = new Member();
        bean.setId(member.getId());
        if (memberWalletHistory.getType().equals(RechargeType.recharge.name())) {
            bean.setWallet(wallet.add(memberWalletHistory.getWallet()));
            memberWalletHistory.setCurrent(wallet.add(memberWalletHistory.getWallet()));
        } else {
            bean.setWallet(member.getWallet().subtract(memberWalletHistory.getWallet()));
            memberWalletHistory.setCurrent(member.getWallet().subtract(memberWalletHistory.getWallet()));
        }
        memberService.updateByIdSelective(bean);*/
        return this.insert(memberWalletHistory);
    }
}
