package cn.xqhuang.dps.config;

import cn.xqhuang.dps.auth.jwt.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import cn.xqhuang.dps.auth.ajax.AjaxAuthenticationResultHandler;
import cn.xqhuang.dps.auth.ajax.AjaxAwareAuthenticationFailureHandler;
import cn.xqhuang.dps.auth.jwt.extractor.JwtHeaderTokenExtractor;
import cn.xqhuang.dps.auth.jwt.extractor.TokenExtractor;
import cn.xqhuang.dps.auth.jwt.verifier.BloomFilterTokenVerifier;
import cn.xqhuang.dps.auth.jwt.verifier.TokenVerifier;
import cn.xqhuang.dps.exception.RestAuthenticationEntryPoint;
import cn.xqhuang.dps.model.token.JwtTokenFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;


@Configuration
@EnableConfigurationProperties(JwtSettings.class)
public class JwtConfiguration {

    @Autowired
    private JwtSettings jwtSettings;

    @Autowired
    private JwtAuthenticationService jwtAuthenticationService;


    @Autowired(required = false)
    private ObjectMapper objectMapper = new ObjectMapper();

    @Bean
    @ConditionalOnMissingBean(JwtAuthenticationService.class)
    public JwtAuthenticationService jwtAuthenticationService() {
        return new JwtAuthenticationServiceAdapter();
    }

    @Bean
    @ConditionalOnMissingBean(JwtTokenFactory.class)
    public JwtTokenFactory jwtTokenFactory() {
        JwtTokenFactory jwtTokenFactory = new JwtTokenFactory();
        jwtTokenFactory.setSettings(jwtSettings);
        return jwtTokenFactory;
    }

    @Bean
    @ConditionalOnMissingBean(TokenExtractor.class)
    public TokenExtractor tokenExtractor() {
        return new JwtHeaderTokenExtractor();
    }

    @Bean
    @ConditionalOnMissingBean(TokenVerifier.class)
    public TokenVerifier tokenVerifier() {
        return new BloomFilterTokenVerifier();
    }

    @Bean
    public JwtAuthenticationProvider jwtAuthenticationProvider() {
        JwtAuthenticationProvider jwtAuthenticationProvider = new JwtAuthenticationProvider();
        jwtAuthenticationProvider.setJwtSettings(jwtSettings);
        jwtAuthenticationProvider.setJwtAuthenticationService(jwtAuthenticationService);
        return jwtAuthenticationProvider;
    }

    @Bean
    @ConditionalOnMissingBean(AuthenticationFailureHandler.class)
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new AjaxAwareAuthenticationFailureHandler(objectMapper);
    }

    @Bean
    @ConditionalOnMissingBean(AuthenticationEntryPoint.class)
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }

    @Bean
    public AjaxAuthenticationResultHandler ajaxAuthenticationResultHandler(@Autowired JwtTokenFactory jwtTokenFactory) {
        AjaxAuthenticationResultHandler ajaxAuthenticationResultHandler = new AjaxAuthenticationResultHandler();
        ajaxAuthenticationResultHandler.setTokenFactory(jwtTokenFactory);
        return ajaxAuthenticationResultHandler;
    }
}
