package cn.xqhuang.dps.endpoint;

import cn.xqhuang.dps.auth.ajax.AjaxAuthenticationResultHandler;
import cn.xqhuang.dps.auth.ajax.FastLoginRequest;
import cn.xqhuang.dps.auth.ajax.LoginRequest;
import cn.xqhuang.dps.user.UserBasicInfo;
import cn.xqhuang.dps.auth.jwt.JwtAuthenticationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AjaxLoginEndpoint {

    private final JwtAuthenticationService jwtAuthenticationService;

    private final AjaxAuthenticationResultHandler ajaxAuthenticationResultHandler;

    public AjaxLoginEndpoint(JwtAuthenticationService jwtAuthenticationService,
                             AjaxAuthenticationResultHandler ajaxAuthenticationResultHandler) {
        this.jwtAuthenticationService = jwtAuthenticationService;
        this.ajaxAuthenticationResultHandler = ajaxAuthenticationResultHandler;
    }

    @PostMapping(path = "${spring.security.auth.login-url:}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest loginRequest, @RequestParam("userType") String userType) {
        if (StringUtils.isBlank(loginRequest.getUsername()) || StringUtils.isBlank(loginRequest.getPassword())) {
            throw new AuthenticationServiceException("请输入用户名和密码");
        }
        UserBasicInfo basicInfo = jwtAuthenticationService.login(loginRequest.getUsername(), loginRequest.getPassword(), userType);
        return ResponseEntity.status(HttpStatus.OK).body(ajaxAuthenticationResultHandler.handleOnSuccess(loginRequest.getUsername(), basicInfo.getRedirectUrl(), basicInfo.getType()));
    }

    @PostMapping(path = "${spring.security.auth.fast-login-url:}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Map<String, Object>> fastLogin(@RequestBody FastLoginRequest fastLoginRequest, String userType) {
        if (StringUtils.isBlank(fastLoginRequest.getMobile()) || StringUtils.isBlank(fastLoginRequest.getDynamicCode())) {
            throw new AuthenticationServiceException("请输入手机号和验证码");
        }
        UserBasicInfo basicInfo = jwtAuthenticationService.fastLogin(fastLoginRequest.getMobile(), fastLoginRequest.getDynamicCode(), userType);
        return ResponseEntity.status(HttpStatus.OK).body(ajaxAuthenticationResultHandler.handleOnSuccess(fastLoginRequest.getMobile(), basicInfo.getRedirectUrl(), basicInfo.getType()));
    }
}
