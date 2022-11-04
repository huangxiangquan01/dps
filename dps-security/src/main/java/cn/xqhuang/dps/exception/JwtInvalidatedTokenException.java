package cn.xqhuang.dps.exception;

import cn.xqhuang.dps.model.token.JwtToken;
import org.springframework.security.core.AuthenticationException;

public class JwtInvalidatedTokenException extends AuthenticationException {

    private JwtToken token;

    public JwtInvalidatedTokenException(JwtToken token, String msg) {
        super(msg);
        this.token = token;
    }

    public String token() {
        return this.token.getToken();
    }
}
