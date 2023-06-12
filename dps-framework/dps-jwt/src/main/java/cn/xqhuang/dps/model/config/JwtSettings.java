package cn.xqhuang.dps.model.config;

import cn.xqhuang.dps.model.token.AccessJwtToken;
import cn.xqhuang.dps.model.token.RefreshToken;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.security.jwt")
public class JwtSettings {

    /**
     * 合作平台用户访问令牌过期时间
     */
    private Integer cooperatedPlatformTokenExpTime;

    /**
     * 外部系统用户访问令牌过期时间
     */
    private Integer externalSysTokenExpTime;

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

    public void setCooperatedPlatformTokenExpTime(Integer cooperatedPlatformTokenExpTime) {
        this.cooperatedPlatformTokenExpTime = cooperatedPlatformTokenExpTime;
    }

    public Integer getCooperatedPlatformTokenExpTime() {
        return cooperatedPlatformTokenExpTime;
    }

    public Integer getExternalSysTokenExpTime() {
        return externalSysTokenExpTime;
    }

    public void setExternalSysTokenExpTime(Integer externalSysTokenExpTime) {
        this.externalSysTokenExpTime = externalSysTokenExpTime;
    }

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
