package cn.xqhuang.dps.service;



import cn.xqhuang.dps.entity.Student;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 *  @author xqhuang
 *
 */

// 定义一些数据操作的接口
public interface StudentService {

    String save(Student student);

    Student getStudentById(Long id);

    void updateStudent(Student student);

    void deleteStudent(Long id);
}
