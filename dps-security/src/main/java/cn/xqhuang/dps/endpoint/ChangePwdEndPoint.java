package cn.xqhuang.dps.endpoint;

import cn.xqhuang.dps.auth.ajax.ChangePwdRequest;
import cn.xqhuang.dps.auth.ajax.ResetPwdRequest;
import cn.xqhuang.dps.auth.jwt.JwtAuthenticationService;
import cn.xqhuang.dps.model.BaseResult;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class ChangePwdEndPoint {

    private final JwtAuthenticationService jwtAuthenticationService;

    public ChangePwdEndPoint(JwtAuthenticationService jwtAuthenticationService) {
        this.jwtAuthenticationService = jwtAuthenticationService;
    }

    @PatchMapping(path = "${spring.security.auth.reset-pwd-url:/api/auth/pwd/reset}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResult<String> resetPwd(@RequestBody final ResetPwdRequest pwdRequest, @RequestParam("userType") final String userType) {
        jwtAuthenticationService.resetPassword(pwdRequest.getUserId(), userType);
        return new BaseResult.Builder<String>()
                .code(BaseResult.SUCCESS)
                .success(BaseResult.TRUE)
                .msg("重置密码成功")
                .build();
    }

    @PatchMapping(path = "${spring.security.auth.change-pwd-url:/api/auth/pwd/modify}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResult<String> resetPwd(@RequestBody final ChangePwdRequest changePwdRequest, @RequestParam("userType") final String userType) {
        jwtAuthenticationService.changePassword(changePwdRequest.getOriginPwd(), changePwdRequest.getNewPwd(), userType);
        return new BaseResult.Builder<String>()
                .code(BaseResult.SUCCESS)
                .success(BaseResult.TRUE)
                .msg("修改密码成功")
                .build();
    }
}
