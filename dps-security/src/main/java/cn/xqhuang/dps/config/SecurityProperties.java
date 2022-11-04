package cn.xqhuang.dps.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.security.auth")
public class SecurityProperties {
    /**
     * used to configure the path of the login rest api
     */
    private String loginUrl;
    /**
     * used to configure the path of the fast login rest api
     */
    private String fastLoginUrl;
    /**
     * used to configure the path of refresh token rest api
     */
    private String refreshTokenUrl;
    /**
     * used to configure the path of retrieve user profile rest api
     */
    private String retrieveUserProfileUrl;

    /**
     * used to change the password of user
     */
    private String changPwdUrl;

    /**
     * used to reset the password of user
     */
    private String resetPwdUrl;
    /**
     * used to configure the root path protected by spring security
     */
    private String apiRootUrl;

    /**
     * 不需要认证的url
     */
    private String[] excludeUrls;

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public String getFastLoginUrl() {
        return fastLoginUrl;
    }

    public void setFastLoginUrl(String fastLoginUrl) {
        this.fastLoginUrl = fastLoginUrl;
    }

    public String getRefreshTokenUrl() {
        return refreshTokenUrl;
    }

    public void setRefreshTokenUrl(String refreshTokenUrl) {
        this.refreshTokenUrl = refreshTokenUrl;
    }

    public String getRetrieveUserProfileUrl() {
        return retrieveUserProfileUrl;
    }

    public void setRetrieveUserProfileUrl(String retrieveUserProfileUrl) {
        this.retrieveUserProfileUrl = retrieveUserProfileUrl;
    }

    public String getChangPwdUrl() {
        return changPwdUrl;
    }

    public void setChangPwdUrl(String changPwdUrl) {
        this.changPwdUrl = changPwdUrl;
    }

    public String getResetPwdUrl() {
        return resetPwdUrl;
    }

    public void setResetPwdUrl(String resetPwdUrl) {
        this.resetPwdUrl = resetPwdUrl;
    }

    public String getApiRootUrl() {
        return apiRootUrl;
    }

    public void setApiRootUrl(String apiRootUrl) {
        this.apiRootUrl = apiRootUrl;
    }

    public String[] getExcludeUrls() {
        return excludeUrls;
    }

    public void setExcludeUrls(String[] excludeUrls) {
        this.excludeUrls = excludeUrls;
    }
}
