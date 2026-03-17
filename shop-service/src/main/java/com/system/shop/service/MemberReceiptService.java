package com.system.shop.service;


import com.system.shop.base.IService;
import com.system.shop.entity.MemberReceipt;
public interface MemberReceiptService extends IService<MemberReceipt> {

    Long update(MemberReceipt memberReceipt, Long memberId);


    MemberReceipt selectByMemberId(Long memberId);
}
