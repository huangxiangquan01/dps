package cn.xqhuang.dps;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 描述
 *
 * @author xiangquan
 * @date 星期四, 4月 27, 2023, 10:00
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class JasyptApplicationTest {

    @Autowired
    private StringEncryptor stringEncryptor;

    /**
     * 加密解密测试
     */
    @Test
    public void jasyptTest() {
        String encrypted = stringEncryptor.encrypt("123456");

        // 加密
        System.out.println(encrypted);
        // 解密
        System.out.println(stringEncryptor.decrypt(encrypted));
    }

    /**
     * 手动测试
     */
    @Test
    public void test() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword("jaspyt_password");
        config.setAlgorithm("PBEWITHHMACSHA512ANDAES_256");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setIvGeneratorClassName("org.jasypt.iv.RandomIvGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);
        System.out.println(encryptor.encrypt("root"));    // JSrINYe4IBotHndGjX1hnmY3mtPNUJlXjP12cx1+pHqUz2FNXGPu3Frnajh3QCXg
    }
}