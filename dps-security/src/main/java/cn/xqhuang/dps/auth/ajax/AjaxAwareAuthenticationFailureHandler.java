package cn.xqhuang.dps.auth.ajax;

import cn.xqhuang.dps.exception.JwtBadTokenException;
import cn.xqhuang.dps.exception.JwtExpiredTokenException;
import cn.xqhuang.dps.exception.UserTypeNotSupportedException;
import cn.xqhuang.dps.model.ErrorCode;
import cn.xqhuang.dps.model.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AjaxAwareAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private static final Logger log = LoggerFactory.getLogger(AjaxAwareAuthenticationFailureHandler.class);
    private final ObjectMapper mapper;

    public AjaxAwareAuthenticationFailureHandler(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException e) throws IOException, ServletException {
        log.error(e.getMessage(), e);

        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ErrorCode errorCode;
        if (e instanceof BadCredentialsException) {
            errorCode = ErrorCode.BAD_CREDENTIAL;
        } else if (e instanceof JwtExpiredTokenException) {
            errorCode = ErrorCode.JWT_TOKEN_EXPIRED;
        } else if (e instanceof JwtBadTokenException) {
            errorCode = ErrorCode.JWT_TOKEN_BAD;
        } else if (e instanceof UsernameNotFoundException) {
            errorCode = ErrorCode.USER_NOT_FOUND;
        } else if (e instanceof DisabledException) {
            errorCode = ErrorCode.USER_DISABLED;
        } else if (e instanceof UserTypeNotSupportedException) {
            errorCode = ErrorCode.USER_TYPE_NOT_SUPPORTED;
        } else {
            errorCode = ErrorCode.AUTHENTICATION;
        }
        mapper.writeValue(response.getWriter(), ErrorResponse.of(e.getMessage(), errorCode, HttpStatus.UNAUTHORIZED));
    }
}