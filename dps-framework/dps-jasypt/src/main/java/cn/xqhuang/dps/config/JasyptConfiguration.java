package cn.xqhuang.dps.config;

import cn.xqhuang.dps.detector.MyEncryptablePropertyDetector;
import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyDetector;
import com.ulisesbocchio.jasyptspringboot.properties.JasyptEncryptorConfigurationProperties;
import com.ulisesbocchio.jasyptspringboot.util.Singleton;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 描述
 *
 * @author xiangquan
 * @date 星期四, 4月 27, 2023, 10:18
 **/
@Configuration
public class JasyptConfiguration {


    /**
     * 加入 StringEncryptor 加密解密类
     *      beanName 必须为 jasyptStringEncryptor 才能是自定义的生效
     *      configProps 为jasypt框架中读取的配置类，就不用自己读取了
     */
    // @Bean("jasyptStringEncryptor")
    public StringEncryptor jasyptStringEncryptor(Singleton<JasyptEncryptorConfigurationProperties> configProps) {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        JasyptEncryptorConfigurationProperties jasyptProperties = configProps.get();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(jasyptProperties.getPassword());
        config.setAlgorithm(jasyptProperties.getAlgorithm());
        config.setKeyObtentionIterations(jasyptProperties.getKeyObtentionIterations());
        config.setPoolSize(jasyptProperties.getPoolSize());
        config.setProviderName(jasyptProperties.getProviderName());
        config.setSaltGeneratorClassName(jasyptProperties.getSaltGeneratorClassname());
        config.setIvGeneratorClassName(jasyptProperties.getIvGeneratorClassname());
        config.setStringOutputType(jasyptProperties.getStringOutputType());
        encryptor.setConfig(config);
        return encryptor;
    }

    /**
     * 自定义属性探测器
     *  beanName为 encryptablePropertyDetector
     */
    @Bean(name = "encryptablePropertyDetector")
    public EncryptablePropertyDetector encryptablePropertyDetector() {
        return new MyEncryptablePropertyDetector();
    }
}
