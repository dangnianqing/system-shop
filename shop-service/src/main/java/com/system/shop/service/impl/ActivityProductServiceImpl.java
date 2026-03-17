package com.system.shop.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.system.shop.base.ServiceImpl;
import com.system.shop.mapper.ActivityProductMapper;
import com.system.shop.entity.ActivityProduct;
import com.system.shop.service.ActivityProductService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ActivityProductServiceImpl extends ServiceImpl<ActivityProductMapper, ActivityProduct> implements ActivityProductService {


    @Override
    public Boolean updateByActivityId(Long activityId, List<Long> productIds) {
        baseMapper.deleteByActivityId(activityId);
        if (CollectionUtil.isNotEmpty(productIds)) {
            List<ActivityProduct> list = new ArrayList<>();
            productIds.forEach(productId -> {
                ActivityProduct activityProduct = new ActivityProduct();
                activityProduct.setActivityId(activityId);
                activityProduct.setProductId(productId);
                list.add(activityProduct);
            });
            this.batchInsert(list);
        }
        return true;
    }

    @Override
    public List<ActivityProduct> selectProductList(Long activityId,Long storeId) {
        return baseMapper.selectProductList(activityId,storeId);
    }

    @Override
    public List<Long> selectProductIds(Long activityId) {
        return baseMapper.selectProductIds(activityId);
    }
}

