package com.system.shop.service;


import com.system.shop.bean.cart.ShoppSettlement;
import com.system.shop.enumer.SettlementType;

public interface SettlementService {

    /**
     * 步骤类型
     *
     */
    SettlementType step();

    /**
     * 计算
     *
     */
    void compute(ShoppSettlement shoppSettlement);

}
