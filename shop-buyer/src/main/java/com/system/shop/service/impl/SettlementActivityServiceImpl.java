package com.system.shop.service.impl;

import cn.hutool.core.collection.CollectionUtil;

import com.system.shop.bean.cart.ProductSettlement;
import com.system.shop.bean.cart.ShoppSettlement;
import com.system.shop.bean.cart.StoreSettlement;
import com.system.shop.entity.Activity;
import com.system.shop.enumer.SettlementType;
import com.system.shop.service.ActivityService;
import com.system.shop.service.SettlementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class SettlementActivityServiceImpl implements SettlementService {
    @Autowired
    private ActivityService activityService;

    @Override
    public SettlementType step() {
        return SettlementType.ACTIVITY;
    }

    @Override
    public void compute(ShoppSettlement shoppSettlement) {
        shoppSettlement.setActivityPrice(shoppSettlement.getActivityPrice() == null ? new BigDecimal("0.00") : shoppSettlement.getActivityPrice());
        for (StoreSettlement storeSettlement : shoppSettlement.getStoreSettlements()) {
            storeSettlement.setActivityPrice(storeSettlement.getActivityPrice() == null ? new BigDecimal("0.00") : storeSettlement.getActivityPrice());
            List<Activity> activityList = activityService.selectActivityByStoreId(storeSettlement.getStoreId());
            List<Long> productSkuIds = new ArrayList<>();
            if (CollectionUtil.isNotEmpty(activityList)) {
                for (Activity activity : activityList) {
                    if (checkActivity(activity, storeSettlement.getProductSettlements(), productSkuIds)) {
                        productSkuIds.addAll(activity.getProductIds());
                        List<ProductSettlement> productSettlementList = storeSettlement.getProductSettlements().stream().filter(productSettlement -> activity.getProductIds().contains(productSettlement.getProductSkuId())).toList();
                        this.sharePrice(activity, productSettlementList, shoppSettlement, storeSettlement);
                    }
                }
            }
        }
    }

    private void sharePrice(Activity activity, List<ProductSettlement> productSettlementList, ShoppSettlement shoppSettlement, StoreSettlement storeSettlement) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (ProductSettlement productSettlement : productSettlementList) {
            totalPrice = totalPrice.add(productSettlement.getPromotionPrice());
        }
        BigDecimal discountTotalPrice = BigDecimal.ZERO;
        if (activity.getType() == 1) {
            discountTotalPrice = activity.getDiscountPrice();
        } else if (activity.getType() == 2) {
            discountTotalPrice = totalPrice.subtract(totalPrice.multiply(activity.getDiscountPrice()).setScale(2, BigDecimal.ROUND_DOWN));
        }
        BigDecimal discountPrice = BigDecimal.ZERO;
        for (int a = 0; a < productSettlementList.size(); a++) {
            ProductSettlement productItem = productSettlementList.get(a);
            BigDecimal price = BigDecimal.ZERO;
            if (a == productSettlementList.size() - 1) {
                price = discountTotalPrice.subtract(discountPrice);
            } else {
                price = discountTotalPrice.multiply(productItem.getPromotionPrice()).divide(totalPrice, 2, RoundingMode.DOWN);
                discountPrice = discountPrice.add(price);
            }
            productItem.setPayPrice(productItem.getPayPrice().subtract(price));
            productItem.setPromotionPrice(productItem.getPromotionPrice().subtract(price));
            productItem.setActivityPrice(productItem.getActivityPrice() == null ? price : productItem.getActivityPrice().add(price));
            productItem.setDiscountPrice(productItem.getDiscountPrice() == null ? price : productItem.getDiscountPrice().add(price));
        }
        storeSettlement.setActivityPrice(storeSettlement.getActivityPrice().add(discountTotalPrice));
        shoppSettlement.setActivityPrice(shoppSettlement.getActivityPrice().add(discountTotalPrice));
    }

    private boolean checkActivity(Activity activity, List<ProductSettlement> productSettlements, List<Long> productSkuIds) {
        List<Long> productSkuIdList = new ArrayList<>();
        int num = 0;
        BigDecimal price = BigDecimal.ZERO;
        for (ProductSettlement productSettlement : productSettlements) {
            if (!productSkuIds.contains(productSettlement.getProductSkuId()) && (activity.getScopeType() == 0 || activity.getProductIds().contains(productSettlement.getProductId()))) {
                productSkuIdList.add(productSettlement.getProductSkuId());
                num = num + productSettlement.getNum();
                price = price.add(productSettlement.getPromotionPrice());
            }
        }
        if (CollectionUtils.isEmpty(productSkuIdList)) {
            return false;
        }
        if (activity.getFullType() != null && activity.getFullType() == 1) {
            if (num >= activity.getFullAmount() && price.compareTo(activity.getFullMoney()) >= 0) {
                activity.setProductIds(productSkuIdList);
                return true;
            }
        } else {
            if (activity.getFullMoney() != null && price.compareTo(activity.getFullMoney()) >= 0) {
                activity.setProductIds(productSkuIdList);
                return true;
            }
            if (activity.getFullAmount() != null && num >= activity.getFullAmount()) {
                activity.setProductIds(productSkuIdList);
                return true;
            }
        }
        return false;
    }
}
