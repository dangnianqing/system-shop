package com.system.shop.enumer;

public enum AfterSaleRefundStatus {

    /**
     * 退款状态枚举
     */
    REFUND_SUCCESS("已退款"),
    REFUND_FAIL("未退款");

    private final String description;

    AfterSaleRefundStatus(String description) {
        this.description = description;
    }

    public String description() {
        return description;
    }
}
