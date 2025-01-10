package cn.xqhuang.dps;

import cn.xqhuang.dps.server.OssTemplate;
import com.amazonaws.services.s3.model.Bucket;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

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
        ossTemplate.createBucket("test1");
    }

    @Test
    public void getAllBuckets() {
        List<Bucket> allBuckets = ossTemplate.getAllBuckets();

        System.out.println(allBuckets.stream().map(Bucket::getName).collect(Collectors.joining(",")));
    }

    @Test
    public void putObject() {
        try {
            File file = new File("/Users/huangxiangquan/Desktop/1.jpg");
            ossTemplate.putObject("test", "/2025/01/10/1.jpg", Files.newInputStream(file.toPath()));
        } catch (Exception e1) {

        }

    }
}
