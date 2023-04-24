package cn.xqhuang.dps.config;


import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DynamicDataSourceConfig {
 
    @Bean("db2")
    // @ConfigurationProperties("spring.datasource.druid.second")
    public DataSource secondDataSource(){
        return DataSourceBuilder
                .create()
                .url("jdbc:mysql://localhost:3306/db1?useUnicode=true&characterEncoding=utf-8&useSSL=false")
                .username("root")
                .password("123456")
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .build();
    }

}
