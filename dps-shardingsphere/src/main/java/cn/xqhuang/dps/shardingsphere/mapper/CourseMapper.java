package cn.xqhuang.dps.shardingsphere.mapper;

import cn.xqhuang.dps.shardingsphere.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * @author ：楼兰
 * @date ：Created in 2020/11/12
 * @description:
 **/
public interface CourseMapper extends BaseMapper<Course> {

    @Select("select * from course")
    List<Course> queryAllCourse();
}
