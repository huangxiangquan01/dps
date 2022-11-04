package cn.xqhuang.dps.auth.ajax;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FastLoginRequest {
    private final String mobile;
    private final String dynamicCode;

    @JsonCreator
    public FastLoginRequest(@JsonProperty("mobile") String mobile, @JsonProperty("dynamicCode") String dynamicCode) {
        this.mobile = mobile;
        this.dynamicCode = dynamicCode;
    }

    public String getMobile() {
        return mobile;
    }

    public String getDynamicCode() {
        return dynamicCode;
    }
}
