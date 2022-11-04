package cn.xqhuang.dps.exception;

import org.springframework.security.core.AuthenticationException;

public class UserTypeNotSupportedException extends AuthenticationException {

    private String userType;

    public UserTypeNotSupportedException(String userType, String msg) {
        super(msg);
        this.userType = userType;
    }

    public UserTypeNotSupportedException(String userType, String msg, Throwable t) {
        super(msg, t);
        this.userType = userType;
    }

    public String getUserType() {
        return userType;
    }
}
