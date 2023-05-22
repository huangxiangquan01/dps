package cn.xqhuang.dps.config;

import cn.xqhuang.dps.annotattion.Log;
import cn.xqhuang.dps.aop.LogAnnotationAdvisor;
import cn.xqhuang.dps.aop.LogAnnotationInterceptor;
import org.springframework.aop.Advisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 描述
 *
 * @author xiangquan
 * @date 星期三, 5月 17, 2023, 09:03
 **/
@Configuration
public class AopConfiguration {

    @Bean
    public Advisor dynamicTransactionAdvisor() {
        LogAnnotationInterceptor interceptor = new LogAnnotationInterceptor();
        return new LogAnnotationAdvisor(interceptor, Log.class);
    }
}
