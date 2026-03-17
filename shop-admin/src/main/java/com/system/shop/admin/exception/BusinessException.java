package com.system.shop.admin.exception;

import com.system.shop.admin.common.ResultCode;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private final int code;
    private final String messageKey;
    private final Object[] args;

    public BusinessException(String messageKey) {
        super(messageKey);
        this.code = ResultCode.ERROR.getCode();
        this.messageKey = messageKey;
        this.args = null;
    }

    public BusinessException(String messageKey, Object... args) {
        super(messageKey);
        this.code = ResultCode.ERROR.getCode();
        this.messageKey = messageKey;
        this.args = args;
    }

    public BusinessException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.code = resultCode.getCode();
        this.messageKey = resultCode.getMessage();
        this.args = null;
    }

    public BusinessException(ResultCode resultCode, Object... args) {
        super(resultCode.getMessage());
        this.code = resultCode.getCode();
        this.messageKey = resultCode.getMessage();
        this.args = args;
    }

    @Override
    public String getMessage() {
        return messageKey;
    }
} 