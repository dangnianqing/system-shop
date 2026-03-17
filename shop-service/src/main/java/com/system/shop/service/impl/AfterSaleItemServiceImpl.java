package com.system.shop.service.impl;

import com.system.shop.base.ServiceImpl;
import com.system.shop.mapper.AfterSaleItemMapper;
import com.system.shop.entity.AfterSaleItem;
import com.system.shop.service.AfterSaleItemService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AfterSaleItemServiceImpl extends ServiceImpl<AfterSaleItemMapper, AfterSaleItem> implements AfterSaleItemService{


    @Override
    public List<AfterSaleItem> selectByAfterSaleCode(String afterSaleCode) {
        return baseMapper.selectByAfterSaleCode(afterSaleCode);
    }
}
