package com.system.shop.enumer;

public enum PayStatus {
    UNPAID("未付款"),
    PAID("已付款");
    private final String desc;

    PayStatus(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
