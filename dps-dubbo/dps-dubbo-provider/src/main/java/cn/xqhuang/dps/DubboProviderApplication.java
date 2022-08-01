package cn.xqhuang.dps;

import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author huangxq
 * @description: TODO
 * @date 2022/5/914:29
 */
@SpringBootApplication
@DubboComponentScan(basePackages = "cn.xqhuang.dps.service.impl")
public class DubboProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboProviderApplication.class);
    }
}
