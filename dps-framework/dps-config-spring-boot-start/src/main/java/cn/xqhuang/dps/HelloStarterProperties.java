package cn.xqhuang.dps;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "dps.config")
public class HelloStarterProperties {

    private String userName;

}
