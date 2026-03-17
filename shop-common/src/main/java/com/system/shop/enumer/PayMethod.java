package com.system.shop.enumer;

public enum PayMethod {
    WECHAT_PAY("微信"),
    ALI_PAY("支付宝"),
    CASH_PAY("现金"),
    BALANCE_PAY("余额");
    private final String desc;

    PayMethod(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
