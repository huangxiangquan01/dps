package cn.xqhuang.dps.mapper;

import cn.xqhuang.dps.entity.Authors;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author huangxq
 * @description:
 * @date 2023/4/11 11:01 星期二
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class AuthorsMapperTest {


    @Resource
    private AuthorsMapper authorsMapper;


    @Test
    public void insert() {
        for (int i = 0; i < 100000; i++) {
            Authors authors = new Authors();
            authors.setFirstName("xq" + i);
            authors.setLastName(i + "h");
            authors.setEmail("huangxq" + i + "@111.com");
            authorsMapper.insert(authors);
        }
    }
}