package com.system.shop.service.impl;

import com.system.shop.base.ServiceImpl;
import com.system.shop.exception.BaseException;
import com.system.shop.result.ResultCode;
import com.system.shop.mapper.ActivityMapper;
import com.system.shop.entity.Activity;
import com.system.shop.service.ActivityProductService;
import com.system.shop.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements ActivityService {
    @Autowired
    private ActivityProductService activityProductService;

    @Override
    public Boolean saveOrUpdate(Activity activity) {
        if (activity.getId() != null) {
            this.updateByIdSelective(activity);
        } else {
            this.insertSelective(activity);
        }
        return activityProductService.updateByActivityId(activity.getId(), activity.getProductIds());
    }

    @Override
    public Activity selectActivity(Long activityId, Long storeId) {
        Activity activity = this.selectById(activityId);
        if (!Objects.equals(storeId, activity.getStoreId())) {
            throw new BaseException(ResultCode.COMMON_FAILED);
        }
        if (activity.getScopeType() == 1) {
            activity.setProductList(activityProductService.selectProductList(activityId, activity.getStoreId()));
        }
        return activity;
    }

    @Override
    public List<Activity> selectActivityByProductIdStoreId(String storeId, String productId) {
        return baseMapper.selectActivityByProductIdStoreId(storeId, productId);
    }

    @Override
    public List<Activity> selectActivityByStoreId(Long storeId) {
        List<Activity> list = baseMapper.selectActivityByStoreId(storeId);
        list.forEach(activity -> {
            if (activity.getScopeType() == 1) {
                activity.setProductIds(activityProductService.selectProductIds(activity.getId()));
            }
        });

        return list;
    }
}
