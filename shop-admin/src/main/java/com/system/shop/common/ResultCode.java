package com.system.shop.common;

import lombok.Getter;

@Getter
public enum ResultCode {
    SUCCESS(200, "success"),
    ERROR(500, "error"),
    UNAUTHORIZED(401, "unauthorized"),
    FORBIDDEN(403, "forbidden"),
    VALIDATE_FAILED(400, "validate.failed"),
    NOT_FOUND(404, "not.found");

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

