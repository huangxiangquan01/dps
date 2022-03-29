package cn.xqhuang.dps.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(HelloStarterProperties.class)
public class HelloStarterAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(HelloStarter.class)
    public HelloStarter helloStarter(HelloStarterProperties helloStarterProperties) {
        return new HelloStarter(helloStarterProperties.getUserName());
    }
}
