package com.system.shop.service.impl;

import com.system.shop.base.ServiceImpl;
import com.system.shop.mapper.StoreMapper;
import com.system.shop.entity.Store;
import com.system.shop.service.StoreService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class StoreServiceImpl extends ServiceImpl<StoreMapper, Store> implements StoreService {


    @Override
    public List<Store> selectByIds(List<Long> storeIds) {
        return baseMapper.selectByIds(storeIds);
    }
}

