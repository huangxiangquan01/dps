package cn.xqhuang.dps.auth.ajax;

import cn.xqhuang.dps.model.token.JwtToken;
import cn.xqhuang.dps.model.token.JwtTokenFactory;

import java.util.HashMap;
import java.util.Map;

public class AjaxAuthenticationResultHandler {

    private JwtTokenFactory tokenFactory;

    public void setTokenFactory(JwtTokenFactory tokenFactory) {
        this.tokenFactory = tokenFactory;
    }

    public Map<String, Object> handleOnSuccess(String username, String redirectUrl, String userType) {
        JwtToken accessToken = tokenFactory.createAccessJwtToken(username, userType);
        JwtToken refreshToken = tokenFactory.createRefreshToken(username, userType);
        Map<String, Object> result = new HashMap<>();
        result.put("token", accessToken.getToken());
        result.put("refreshToken", refreshToken.getToken());
        result.put("redirectUrl", redirectUrl);
        return result;
    }
}
