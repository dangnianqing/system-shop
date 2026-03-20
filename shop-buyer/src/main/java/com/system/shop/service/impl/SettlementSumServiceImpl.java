package com.system.shop.service.impl;

import com.system.shop.bean.cart.ShoppSettlement;
import com.system.shop.enumer.SettlementType;
import com.system.shop.service.SettlementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SettlementSumServiceImpl implements SettlementService {
    @Override
    public SettlementType step() {
        return SettlementType.SUM;
    }

    @Override
    public void compute(ShoppSettlement shoppSettlement) {

        shoppSettlement.getStoreSettlements().forEach(storeSettlement -> {
            storeSettlement.setTotalPrice(storeSettlement.getProductPrice().add(storeSettlement.getFreightPrice()));
            storeSettlement.setDiscountPrice(storeSettlement.getActivityPrice().add(storeSettlement.getCouponPrice()));

            storeSettlement.setPayPrice(
                    storeSettlement.getProductPrice()
                            .add(storeSettlement.getFreightPrice())
                            .subtract(storeSettlement.getCouponPrice())
                            .subtract(storeSettlement.getActivityPrice())
            );
        });


        shoppSettlement.setPayPrice(
                shoppSettlement.getProductPrice()
                        .add(shoppSettlement.getFreightPrice())
                        .subtract(shoppSettlement.getCouponPrice())
                        .subtract(shoppSettlement.getActivityPrice())
        );

        shoppSettlement.setTotalPrice(shoppSettlement.getProductPrice().add(shoppSettlement.getFreightPrice()));
        shoppSettlement.setDiscountPrice(shoppSettlement.getActivityPrice().add(shoppSettlement.getCouponPrice()));


    }
}
