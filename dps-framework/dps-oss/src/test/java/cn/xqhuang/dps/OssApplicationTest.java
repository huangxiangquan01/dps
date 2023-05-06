package cn.xqhuang.dps;

import cn.xqhuang.dps.server.OssTemplate;
import com.amazonaws.services.s3.model.S3Object;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * 描述
 *
 * @author xiangquan
 * @date 星期四, 5月 04, 2023, 15:48
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class OssApplicationTest {

    @Resource
    private OssTemplate ossTemplate;

    @Test
    public void ossTest() {
        S3Object object = ossTemplate.getObject("kubenertes", "/images/1-1.png");
    }
}