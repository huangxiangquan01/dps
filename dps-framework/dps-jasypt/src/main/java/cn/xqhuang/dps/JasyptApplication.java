package cn.xqhuang.dps;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author huangxq
 * @date 2023/4/26 09:13
 */
@SpringBootApplication
@MapperScan(basePackages = {"cn.xqhuang.dps.mapper"})
public class JasyptApplication {
    public static void main(String[] args) {
        SpringApplication.run(JasyptApplication.class);
    }
}
