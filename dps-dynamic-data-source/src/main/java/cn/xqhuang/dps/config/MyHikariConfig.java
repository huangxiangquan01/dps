package cn.xqhuang.dps.config;

import com.zaxxer.hikari.HikariConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("spring.datasource.hikari")
public class MyHikariConfig extends HikariConfig {


}