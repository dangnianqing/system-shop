package com.system.shop.service.impl;

import com.system.shop.bean.cart.ShoppSettlement;
import com.system.shop.enumer.SettlementType;
import com.system.shop.service.SettlementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
public class SettlementCouponServiceImpl implements SettlementService {
    @Override
    public SettlementType step() {
        return SettlementType.COUPON;
    }

    @Override
    public void compute(ShoppSettlement shoppSettlement) {
        shoppSettlement.setCouponPrice(shoppSettlement.getCouponPrice() == null ? new BigDecimal("0.00") : shoppSettlement.getCouponPrice());
        shoppSettlement.getStoreSettlements().forEach(storeSettlement -> {
            storeSettlement.setCouponPrice(storeSettlement.getCouponPrice() == null ? new BigDecimal("0.00") : storeSettlement.getCouponPrice());
        });
    }
}
