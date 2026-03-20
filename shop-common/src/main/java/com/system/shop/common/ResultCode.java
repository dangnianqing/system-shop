package com.system.shop.common;

import lombok.Getter;

@Getter
public enum ResultCode {
    SUCCESS(200, "success"),
    ERROR(500, "error"),
    UNAUTHORIZED(401, "unauthorized"),
    FORBIDDEN(403, "forbidden"),
    VALIDATE_FAILED(400, "validate.failed"),
    NOT_FOUND(404, "not.found"),
    PARAM_ERROR(400, "param.error"),
    DATA_CHECK_ERROR(400, "data.check.error"),
    ORDER_NOT_EXIST(400, "order.not.exist"),
    ORDER_CODE_EMPTY(400, "order.code.empty"),
    ORDER_STATUS_ERROR(400, "order.status.error"),
    ORDER_CANNOT_CANCEL(400, "order.cannot.cancel"),
    ORDER_ALREADY_CANCELLED(400, "order.already.cancelled"),
    ORDER_ALREADY_PAID(400, "order.already.paid"),
    INSUFFICIENT_BALANCE(400, "insufficient.balance"),
    ORDER_CANCEL_STATUS_MODIFY(400, "order.cancel.status.modify"),
    ORDER_NOT_SUPPORT_DISTRIBUTION(400, "order.not.support.distribution"),
    ORDER_NOT_EXIST_VALID(400, "order.not.exist.valid"),
    PRODUCT_NOT_EXIST(400, "product.not.exist"),
    PRODUCT_UNAUDITED(400, "product.unaudited"),
    PRODUCT_INSUFFICIENT_STOCK(400, "product.insufficient.stock"),
    MEMBER_NOT_EXIST(400, "member.not.exist"),
    MEMBER_NOT_LOGIN(401, "member.not.login"),
    MEMBER_LOGIN_EXPIRED(401, "member.login.expired"),
    AFTER_SALES_ORDER_STATUS_ERROR(400, "after.sales.order.status.error"),
    AFTER_SALES_ITEM_STATUS_ERROR(400, "after.sales.item.status.error"),
    AFTER_SALES_QUANTITY_ERROR(400, "after.sales.quantity.error"),
    AFTER_SALES_REFUND_AMOUNT_ERROR(400, "after.sales.refund.amount.error"),
    AFTER_SALES_REFUND_FAILED(400, "after.sales.refund.failed");

    private final Integer code;
    private final String messageKey;

    ResultCode(Integer code, String messageKey) {
        this.code = code;
        this.messageKey = messageKey;
    }

    public String getMessage() {
        return messageKey;
    }
}

