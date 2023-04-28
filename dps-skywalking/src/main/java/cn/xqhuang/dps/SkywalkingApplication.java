package cn.xqhuang.dps;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"cn.xqhuang.dps.mapper"})
public class SkywalkingApplication {

    public static void main(String[] args) {
        SpringApplication.run(SkywalkingApplication.class);
    }
}
