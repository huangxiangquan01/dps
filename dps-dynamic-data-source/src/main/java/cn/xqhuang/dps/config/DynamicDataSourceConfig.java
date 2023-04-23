/*package cn.xqhuang.dps.config;


@Configuration
public class DynamicDataSourceConfig {
    @Bean("db1")
    @ConfigurationProperties("spring.datasource.druid.master")
    public DataSource firstDataSource(){
        return DruidDataSourceBuilder.create().build();
    }
 
    @Bean("db2")
    @ConfigurationProperties("spring.datasource.druid.second")
    public DataSource secondDataSource(){
        return DruidDataSourceBuilder.create().build();
    }

}*/
