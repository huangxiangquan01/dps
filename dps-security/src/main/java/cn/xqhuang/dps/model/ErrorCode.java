package cn.xqhuang.dps.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ErrorCode {
    GLOBAL(2),
    AUTHENTICATION(10),
    BAD_CREDENTIAL(99),
    JWT_TOKEN_EXPIRED(11),
    JWT_TOKEN_BAD(44),
    USER_NOT_FOUND(12),
    USER_DISABLED(20),
    USER_TYPE_NOT_SUPPORTED(36);

    private int errorCode;

    ErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    @JsonValue
    public int getErrorCode() {
        return errorCode;
    }
}
