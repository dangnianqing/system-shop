package com.system.shop.service;


import com.system.shop.base.IService;
import com.system.shop.entity.MemberWalletHistory;
public interface MemberWalletHistoryService extends IService<MemberWalletHistory> {


    Boolean saveWallet(MemberWalletHistory memberWalletHistory);
}
