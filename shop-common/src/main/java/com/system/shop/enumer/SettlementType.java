package com.system.shop.enumer;

public enum SettlementType {


    /**
     * 计算步骤
     */

    INIT("校验商品"),
    ACTIVITY("商品促销计算"),
    COUPON("优惠券计算"),
    FREIGHT("运费计算"),
    SUM("总和");
    private String distribution;

    SettlementType(String distribution) {
        this.distribution = distribution;
    }

    public String getDistribution() {
        return distribution;
    }



}
