package cn.xqhuang.dps;

import cn.xqhuang.dps.config.SecurityConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import java.lang.annotation.*;

@EnableWebSecurity

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({SecurityConfiguration.class})
public @interface EnableSecurity {

}
