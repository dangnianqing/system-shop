package com.system.shop.service.impl;

import com.system.shop.base.ServiceImpl;
import com.system.shop.mapper.AfterSaleLogMapper;
import com.system.shop.entity.AfterSaleLog;
import com.system.shop.service.AfterSaleLogService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AfterSaleLogServiceImpl extends ServiceImpl<AfterSaleLogMapper, AfterSaleLog> implements AfterSaleLogService{


    @Override
    public List<AfterSaleLog> selectByAfterSaleCode(String afterSaleCode) {
        return baseMapper.selectByAfterSaleCode(afterSaleCode);
    }
}
