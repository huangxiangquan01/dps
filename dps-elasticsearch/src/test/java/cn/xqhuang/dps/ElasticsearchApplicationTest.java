package cn.xqhuang.dps;

import cn.xqhuang.dps.entity.Student;
import cn.xqhuang.dps.service.StudentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;


/**
 * @author huangxq
 * @description: TODO
 * @date 2022/11/1113:26
 */
@SpringBootTest(classes = {ElasticsearchApplication.class})
@RunWith(SpringRunner.class)
public class ElasticsearchApplicationTest {

    @Resource
    private StudentService studentService;

    @Test
    public void add() {
        studentService.save(Student.builder()
                .id(1L)
                .age(23)
                .email("aaa@133.com")
                .name("张三")
                .testNumber("221")
                .birthDay(new Date())
                .build());
    }


    @Test
    public void getById() {
        Student student = studentService.getStudentById(1L);
        System.out.println(student.toString());
    }

    @Test
    public void update() {
        Student student = studentService.getStudentById(1L);
        student.setAge(student.getAge() + 1);
        studentService.updateStudent(student);
    }

    @Test
    public void deleteById() {
       studentService.deleteStudent(1L);
    }
}