package com.system.shop.service;


import com.system.shop.base.IService;
import com.system.shop.entity.MemberAddress;

public interface MemberAddressService extends IService<MemberAddress> {

    Boolean delete(Long id, Long memberId);

    MemberAddress selectByDefaultMemberId(Long memberId);

    Boolean saveOrUpdate(MemberAddress memberAddress);
}
