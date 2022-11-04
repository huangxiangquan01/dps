package cn.xqhuang.dps.config;

import cn.xqhuang.dps.exception.JwtBadTokenException;
import cn.xqhuang.dps.exception.JwtExpiredTokenException;
import cn.xqhuang.dps.exception.UserTypeNotSupportedException;
import cn.xqhuang.dps.model.ErrorCode;
import cn.xqhuang.dps.model.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Configuration
@ConditionalOnBean(SecurityConfiguration.Marker.class)
@EnableConfigurationProperties(SecurityProperties.class)
@Import({ WebSecurityConfiguration.class, JwtConfiguration.class, PasswordEncoderConfiguration.class, CustomWebMvcConfiguration.class })
public class SecurityAutoConfiguration {

    @ControllerAdvice
    @ConditionalOnClass(AuthenticationException.class)
    public static class AuthenticationExceptionHandler {
        private static final Logger log = LoggerFactory.getLogger(AuthenticationExceptionHandler.class);

        public AuthenticationExceptionHandler() {
            if (log.isInfoEnabled()) {
                log.info("AuthenticationExceptionHandler is instantiated");
            }
        }

        @ExceptionHandler(AuthenticationException.class)
        public ResponseEntity<ErrorResponse> handlerServiceException(AuthenticationException e) {
            log.error(e.getMessage(), e);

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

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorResponse.of(e.getMessage(), errorCode, HttpStatus.UNAUTHORIZED));
        }
    }
}
