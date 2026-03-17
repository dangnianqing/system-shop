package com.system.shop.service.impl;

import com.system.shop.base.ServiceImpl;
import com.system.shop.mapper.MemberWxMapper;
import com.system.shop.entity.MemberWx;
import com.system.shop.service.MemberWxService;
import org.springframework.stereotype.Service;


@Service
public class MemberWxServiceImpl extends ServiceImpl<MemberWxMapper, MemberWx> implements MemberWxService {


    @Override
    public MemberWx selectByMiniAppOpenId(String miniAppOpenId) {
        return baseMapper.selectByMiniAppOpenId(miniAppOpenId);
    }
}

