package com.system.shop.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.system.shop.base.ServiceImpl;
import com.system.shop.mapper.MemberAddressMapper;
import com.system.shop.entity.MemberAddress;
import com.system.shop.service.MemberAddressService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
public class MemberAddressServiceImpl extends ServiceImpl<MemberAddressMapper, MemberAddress> implements MemberAddressService {




    @Override
    public Boolean delete(Long id, Long memberId) {
        MemberAddress memberAddress = this.selectById(id);
        if (Objects.equals(memberAddress.getMemberId(), memberId)) {
            return deleteById(id);
        }
        return false;
    }


    @Override
    public MemberAddress selectByDefaultMemberId(Long memberId) {
        List<MemberAddress> list = this.selectList(new HashMap<String, Object>() {{
            put("memberId", memberId);
            put("isDefault", true);
        }});

        return CollectionUtil.isNotEmpty(list) ? list.get(0) : null;
    }

    @Override
    public Boolean saveOrUpdate(MemberAddress memberAddress) {
        if (memberAddress.getId() == null) {
            return this.insert(memberAddress);
        } else {
            return this.updateByIdSelective(memberAddress);
        }
    }
}
