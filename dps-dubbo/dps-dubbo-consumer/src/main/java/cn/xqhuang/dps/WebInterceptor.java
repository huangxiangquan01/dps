package cn.xqhuang.dps;

import cn.xqhuang.dps.dubboInterceptor.ConsumerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author huangxq
 * @description: TODO
 * @date 2022/5/915:50
 */
@Configuration
public class WebInterceptor implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ConsumerInterceptor());
    }

}
