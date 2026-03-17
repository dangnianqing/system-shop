package com.system.shop.service.impl;

import com.system.shop.base.ServiceImpl;
import com.system.shop.mapper.MemberReceiptMapper;
import com.system.shop.entity.MemberReceipt;
import com.system.shop.service.MemberReceiptService;
import org.springframework.stereotype.Service;

@Service
public class MemberReceiptServiceImpl extends ServiceImpl<MemberReceiptMapper, MemberReceipt> implements MemberReceiptService {


    @Override
    public Long update(MemberReceipt memberReceipt, Long memberId) {
        memberReceipt.setMemberId(memberId);
        MemberReceipt bean = this.selectByMemberId(memberId);
        if (bean != null) {
            memberReceipt.setId(bean.getId());
            baseMapper.updateByIdSelective(memberReceipt);
            return memberReceipt.getId();
        }
        baseMapper.insert(memberReceipt);
        return memberReceipt.getId();
    }

    @Override
    public MemberReceipt selectByMemberId(Long memberId) {
        return baseMapper.selectByMemberId(memberId);
    }
}
