package cn.xqhuang.dps.config;

import cn.xqhuang.dps.interceptor.ApiAccessHandlerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class CustomWebMvcConfiguration implements WebMvcConfigurer {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getHandlerInterceptor()).addPathPatterns(securityProperties.getApiRootUrl());
    }

    private HandlerInterceptor getHandlerInterceptor() {
        return new ApiAccessHandlerInterceptor();
    }
}
