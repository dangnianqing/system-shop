package com.system.shop.service.impl;

import com.system.shop.base.ServiceImpl;
import com.system.shop.enumer.AfterSaleType;
import com.system.shop.mapper.AfterSaleReasonMapper;
import com.system.shop.entity.AfterSaleReason;
import com.system.shop.service.AfterSaleReasonService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AfterSaleReasonServiceImpl extends ServiceImpl<AfterSaleReasonMapper, AfterSaleReason> implements AfterSaleReasonService {


    @Override
    public Boolean init() {
        List<AfterSaleReason> list = new ArrayList<>();
        list.add(new AfterSaleReason("实际商品与描述不符", AfterSaleType.RETURN_REFUND.name()));
        list.add(new AfterSaleReason("质量问题", AfterSaleType.RETURN_REFUND.name()));
        list.add(new AfterSaleReason("少件/漏发", AfterSaleType.RETURN_REFUND.name()));
        list.add(new AfterSaleReason("包装/商品/污迹/裂痕/变形", AfterSaleType.RETURN_REFUND.name()));
        list.add(new AfterSaleReason("商家发错货", AfterSaleType.RETURN_REFUND.name()));
        list.add(new AfterSaleReason("不合适", AfterSaleType.RETURN_REFUND.name()));
        list.add(new AfterSaleReason("不喜欢", AfterSaleType.RETURN_REFUND.name()));



        list.add(new AfterSaleReason("发货太慢", AfterSaleType.REFUND.name()));
        list.add(new AfterSaleReason("物流配送太慢", AfterSaleType.REFUND.name()));
        list.add(new AfterSaleReason("不想要了", AfterSaleType.REFUND.name()));

        return this.batchInsert(list);


    }
}
