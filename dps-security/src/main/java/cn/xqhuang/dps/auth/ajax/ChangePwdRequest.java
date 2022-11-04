package cn.xqhuang.dps.auth.ajax;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ChangePwdRequest {
    private final String originPwd;
    private final String newPwd;

    public ChangePwdRequest(@JsonProperty("originPwd") final String originPwd,
                            @JsonProperty("newPwd") final String newPwd) {
        this.originPwd = originPwd;
        this.newPwd = newPwd;
    }

    public String getOriginPwd() {
        return originPwd;
    }

    public String getNewPwd() {
        return newPwd;
    }
}
