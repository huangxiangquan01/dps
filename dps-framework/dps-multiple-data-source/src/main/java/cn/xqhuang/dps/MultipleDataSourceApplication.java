package cn.xqhuang.dps;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
@MapperScan(basePackages = "cn.xqhuang.dps.mapper")
public class MultipleDataSourceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MultipleDataSourceApplication.class);
    }
}
