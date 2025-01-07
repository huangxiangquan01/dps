package cn.xqhuang.dps;

import cn.xqhuang.dps.entity.User;
import cn.xqhuang.dps.service.UserMongoMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(classes = MongoApplication.class)
public class MongoApplicationTest {

    @Autowired
    private UserMongoMapper userMongoMapper;

    @Test
    public void add() {
        System.out.println(("----- selectAll method test ------"));
        User user = new User();
        user.setId("1");
        user.setName("张三");
        user.setAge(18L);
        user.setEmail("zhangsan@11.com");
        userMongoMapper.save(user);
    }
}
