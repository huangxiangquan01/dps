package cn.xqhuang.dps;

import cn.xqhuang.dps.filter.SensitiveFilter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author xiangquan
 * @Description
 * @date 星期一, 5月 22, 2023, 11:01
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class BootCodeApplicationTest {

    // 两种方式都可以 非法敏感词汇判断
    @Test
    public void sensitive(){
        try {
            String searchKey = "傻逼h";
            String placeholder = "***";
            //非法敏感词汇判断
            SensitiveFilter filter = SensitiveFilter.getInstance();
            String s = filter.replaceSensitiveWord(searchKey, 1, placeholder);
            System.out.println(s);
            int n = filter.CheckSensitiveWord(searchKey, 0, 2);
            //存在非法字符
            if (n > 0) {
                System.out.println(String.format("这个人输入了非法字符--> %s,不知道他到底要查什么~ userid--> %d", searchKey, 1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}