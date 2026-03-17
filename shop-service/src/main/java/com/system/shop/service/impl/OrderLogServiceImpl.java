package com.system.shop.service.impl;

import com.system.shop.base.ServiceImpl;
import com.system.shop.mapper.OrderLogMapper;
import com.system.shop.entity.OrderLog;
import com.system.shop.service.OrderLogService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderLogServiceImpl extends ServiceImpl<OrderLogMapper,OrderLog> implements OrderLogService{


    @Override
    public List<OrderLog> selectByOrderCode(String orderCode) {
        return baseMapper.selectByOrderCode(orderCode);
    }
}
