package cn.xqhuang.dps.shardingsphere;

import cn.xqhuang.dps.shardingsphere.entity.Course;
import cn.xqhuang.dps.shardingsphere.mapper.CourseMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author huangxq
 * @description TODO
 * @date 2023/2/19 20:41
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ShardingSphereApplicationTest {

    @Resource
    private CourseMapper courseMapper;

    @Test
    public void addCourses() {
        for (int i = 0; i < 10; i++) {
            Course course = new Course();
            course.setCname("java_" + i);
            course.setUserId(1001L);
            course.setCstatus("1");

            courseMapper.insert(course);
        }
    }
}