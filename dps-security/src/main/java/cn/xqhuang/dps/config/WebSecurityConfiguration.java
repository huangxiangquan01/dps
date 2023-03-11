package cn.xqhuang.dps.config;

import cn.xqhuang.dps.auth.jwt.JwtAuthenticationProvider;
import cn.xqhuang.dps.auth.jwt.JwtTokenAuthenticationProcessingFilter;
import cn.xqhuang.dps.auth.jwt.SkipPathRequestMatcher;
import cn.xqhuang.dps.auth.jwt.extractor.TokenExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    public static final String AUTHENTICATION_HEADER_NAME = "Authorization";

    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    private AuthenticationFailureHandler failureHandler;
    @Autowired
    private JwtAuthenticationProvider jwtAuthenticationProvider;
    @Autowired
    private TokenExtractor tokenExtractor;
    @Autowired
    private AuthenticationManager authenticationManager;

    protected JwtTokenAuthenticationProcessingFilter buildJwtTokenAuthenticationProcessingFilter(List<String> pathsToSkip, String pattern) throws Exception {
        SkipPathRequestMatcher matcher = new SkipPathRequestMatcher(pathsToSkip, pattern);
        JwtTokenAuthenticationProcessingFilter filter
                = new JwtTokenAuthenticationProcessingFilter(failureHandler, tokenExtractor, matcher);
        filter.setAuthenticationManager(this.authenticationManager);
        return filter;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(jwtAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        List<String> permitAllEndpointList = getUnAuthUrls();

        http.csrf().disable() // We don't need CSRF for JWT based authentication
                .exceptionHandling()
                .authenticationEntryPoint(this.authenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(permitAllEndpointList.toArray(new String[permitAllEndpointList.size()]))
                .permitAll()
                .and()
                .authorizeRequests()
                .antMatchers(securityProperties.getApiRootUrl()).authenticated() // Protected API End-points
                .and()
                .addFilterBefore(new CustomCorsFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(buildJwtTokenAuthenticationProcessingFilter(permitAllEndpointList, securityProperties.getApiRootUrl()),
                        UsernamePasswordAuthenticationFilter.class);
    }


    private List<String> getUnAuthUrls() {
        List<String> unAuthUrls = new ArrayList<>();
        unAuthUrls.add("/console");

        if (!StringUtils.isEmpty(securityProperties.getLoginUrl())) {
            unAuthUrls.add(securityProperties.getLoginUrl());
        }

        if (!StringUtils.isEmpty(securityProperties.getFastLoginUrl())) {
            unAuthUrls.add(securityProperties.getFastLoginUrl());
        }

        if (!StringUtils.isEmpty(securityProperties.getRefreshTokenUrl())) {
            unAuthUrls.add(securityProperties.getRefreshTokenUrl());
        }

        if (Objects.nonNull(securityProperties.getExcludeUrls()) && securityProperties.getExcludeUrls().length > 0) {
            for (String url : securityProperties.getExcludeUrls()) {
                if (!StringUtils.isEmpty(url)) {
                    unAuthUrls.add(url);
                }
            }
        }

        return unAuthUrls;
    }
}
