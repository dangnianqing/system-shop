package com.system.shop.enumer;


public enum OrderItemAfterSaleStatus {
    NEW("新订单，不能申请售后"),
    NOT_APPLIED("未申请"),
    ALREADY_APPLIED("已申请"),
    EXPIRED("已失效不允许申请售后"),
    PART_AFTER_SALE("部分售后");


    private final String desc;

    OrderItemAfterSaleStatus(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

}
