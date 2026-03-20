package com.system.shop.service.impl;

import com.system.shop.base.ServiceImpl;
import com.system.shop.mapper.LogisticsMapper;
import com.system.shop.entity.Logistics;
import com.system.shop.service.LogisticsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogisticsServiceImpl extends ServiceImpl<LogisticsMapper, Logistics> implements LogisticsService {

    @Override
    public Boolean deleteList(List<Long> ids) {
        return baseMapper.deleteList(ids);
    }

    @Override
    public Boolean saveOrUpdate(Logistics logistics) {
        Logistics bean = this.selectById(logistics.getId());
        if (bean != null) {
            return baseMapper.updateByIdSelective(logistics);
        } else {
            return baseMapper.insert(logistics);
        }
    }

    @Override
    public List<Logistics> selectStoreList(Long storeId) {
        return baseMapper.selectStoreList(storeId);
    }

}
