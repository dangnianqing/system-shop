package com.system.shop.service.impl;

import com.system.shop.base.ServiceImpl;
import com.system.shop.mapper.StoreDetailMapper;
import com.system.shop.entity.StoreDetail;
import com.system.shop.service.StoreDetailService;
import com.system.shop.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreDetailServiceImpl extends ServiceImpl<StoreDetailMapper, StoreDetail> implements StoreDetailService {
    @Autowired
    private StoreService storeService;

    @Override
    public StoreDetail selectByStoreId(Long storeId) {
        return baseMapper.selectByStoreId(storeId);
    }

    @Override
    public Boolean saveOrUpdate(StoreDetail storeDetail) {
        storeDetail.setStoreName(storeService.selectById(storeDetail.getStoreId()).getStoreName());
        StoreDetail bean = baseMapper.selectByStoreId(storeDetail.getStoreId());
        if (bean != null) {
            storeDetail.setId(bean.getId());
            return baseMapper.updateByIdSelective(storeDetail);
        } else {
            return baseMapper.insertSelective(storeDetail);
        }
    }
}
