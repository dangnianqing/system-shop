package com.system.shop.enumer;

public enum AfterSaleType {
    CANCEL("取消订单"),
    RETURN_REFUND("退货退款"),
    REFUND("退款"),
    COMPLAIN("投诉");

    private final String desc;

    AfterSaleType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
