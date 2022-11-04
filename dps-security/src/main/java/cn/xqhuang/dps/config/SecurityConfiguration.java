package cn.xqhuang.dps.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfiguration {

    static class Marker {}
    @Bean
    public Marker enableSecurityMarker() {
        return new Marker();
    }
}
