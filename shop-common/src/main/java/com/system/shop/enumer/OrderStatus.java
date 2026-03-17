package com.system.shop.enumer;

public enum OrderStatus {

    PENDING_PAYMENT("待付款"),
    PENDING_DELIVERY("待发货"),
    PENDING_RECEIPT("待收货"),
    COMPLETE("已完成"),
    CANCELLED("已取消");
    private final String desc;

    OrderStatus(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
