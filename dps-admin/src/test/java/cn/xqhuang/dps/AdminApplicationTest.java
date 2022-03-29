package cn.xqhuang.dps;


import cn.xqhuang.dps.config.HelloStarter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AdminApplicationTest {

    @Autowired
    private HelloStarter helloStarter;

    @Test
    public void testAutoConfiguration() {
        System.out.println(helloStarter.getName());
    }
}