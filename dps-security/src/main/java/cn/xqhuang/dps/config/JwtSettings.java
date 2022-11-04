package cn.xqhuang.dps.config;

import cn.xqhuang.dps.model.token.AccessJwtToken;
import cn.xqhuang.dps.model.token.RefreshToken;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.security.jwt")
public class JwtSettings {


    /**
     * {@link AccessJwtToken} will expire after this time.
     */
    private Integer tokenExpirationTime;

    /**
     * {@link RefreshToken} will expire after this time.
     */
    private Integer refreshTokenExpTime;

    /**
     * Token issuer.
     */
    private String tokenIssuer;

    /**
     * Key is used to sign {@link AccessJwtToken}.
     */
    private String tokenSigningKey;

    /**
     * Key is used to sign {@link RefreshToken}.
     */
    private String refreshTokenSigningKey;

    public Integer getTokenExpirationTime() {
        return tokenExpirationTime;
    }

    public void setTokenExpirationTime(Integer tokenExpirationTime) {
        this.tokenExpirationTime = tokenExpirationTime;
    }

    public Integer getRefreshTokenExpTime() {
        return refreshTokenExpTime;
    }

    public void setRefreshTokenExpTime(Integer refreshTokenExpTime) {
        this.refreshTokenExpTime = refreshTokenExpTime;
    }

    public String getTokenIssuer() {
        return tokenIssuer;
    }

    public void setTokenIssuer(String tokenIssuer) {
        this.tokenIssuer = tokenIssuer;
    }

    public String getTokenSigningKey() {
        return tokenSigningKey;
    }

    public void setTokenSigningKey(String tokenSigningKey) {
        this.tokenSigningKey = tokenSigningKey;
    }

    public String getRefreshTokenSigningKey() {
        return refreshTokenSigningKey;
    }

    public void setRefreshTokenSigningKey(String refreshTokenSigningKey) {
        this.refreshTokenSigningKey = refreshTokenSigningKey;
    }
}
