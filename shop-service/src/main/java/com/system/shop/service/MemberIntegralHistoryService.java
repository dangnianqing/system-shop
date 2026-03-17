package com.system.shop.service;


import com.system.shop.base.IService;
import com.system.shop.entity.MemberIntegralHistory;
public interface MemberIntegralHistoryService extends IService<MemberIntegralHistory> {


    Boolean saveIntegral(MemberIntegralHistory memberIntegralHistory);
}
