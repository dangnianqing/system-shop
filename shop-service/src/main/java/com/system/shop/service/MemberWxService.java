package com.system.shop.service;

import com.system.shop.base.IService;
import com.system.shop.entity.MemberWx;

public interface MemberWxService extends IService<MemberWx> {


    MemberWx selectByMiniAppOpenId(String miniAppOpenId);
}

