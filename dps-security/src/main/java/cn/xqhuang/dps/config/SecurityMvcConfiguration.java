/*
package cn.xqhuang.dps.config;

import cn.xqhuang.dps.auth.ajax.AjaxAuthenticationResultHandler;
import cn.xqhuang.dps.auth.jwt.JwtAuthenticationService;
import cn.xqhuang.dps.auth.jwt.extractor.TokenExtractor;
import cn.xqhuang.dps.auth.jwt.verifier.TokenVerifier;
import cn.xqhuang.dps.endpoint.AjaxLoginEndpoint;
import cn.xqhuang.dps.endpoint.ChangePwdEndPoint;
import cn.xqhuang.dps.endpoint.RefreshTokenEndpoint;
import cn.xqhuang.dps.endpoint.RetrieveUserProfileEndpoint;
import cn.xqhuang.dps.model.token.JwtTokenFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(JwtSettings.class)
@ConditionalOnWebApplication
public class SecurityMvcConfiguration {

    @Autowired
    private JwtAuthenticationService jwtAuthenticationService;

    @Autowired
    private AjaxAuthenticationResultHandler ajaxAuthenticationResultHandler;

    @Autowired
    private JwtTokenFactory tokenFactory;

    @Autowired
    private JwtSettings jwtSettings;

    @Autowired
    private TokenVerifier tokenVerifier;

    @Autowired
    private TokenExtractor tokenExtractor;

    @Bean
    public AjaxLoginEndpoint ajaxLoginEndpoint() {
        return new AjaxLoginEndpoint(jwtAuthenticationService, ajaxAuthenticationResultHandler);
    }

    @Bean
    public RefreshTokenEndpoint refreshTokenEndpoint() {
        return new RefreshTokenEndpoint(jwtAuthenticationService, tokenVerifier, tokenExtractor, tokenFactory, jwtSettings);
    }

    @Bean
    public RetrieveUserProfileEndpoint retrieveUserProfileEndpoint() {
        return new RetrieveUserProfileEndpoint();
    }

    @Bean
    public ChangePwdEndPoint changePwdEndPoint() {
        return new ChangePwdEndPoint(jwtAuthenticationService);
    }
}
*/
