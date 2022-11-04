package cn.xqhuang.dps.auth.ajax;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResetPwdRequest {
    private final Long userId;

    public ResetPwdRequest(@JsonProperty("userId") final Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }
}
