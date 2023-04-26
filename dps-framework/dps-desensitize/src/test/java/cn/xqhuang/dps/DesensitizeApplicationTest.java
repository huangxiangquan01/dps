package cn.xqhuang.dps;

import cn.xqhuang.dps.utils.JsonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author huangxq
 * @description:
 * @date 2023/4/26 09:15 星期三
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class DesensitizeApplicationTest {


    @Test
    public void test01() {
        User user = new User();
        user.setId(1L);
        user.setName("一二三四");

        System.out.println(JsonUtil.toJson(user));
    }
}