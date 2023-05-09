package cn.xqhuang.dps.service.impl;

import cn.xqhuang.dps.entity.Student;
import cn.xqhuang.dps.repository.StudentRepository;
import cn.xqhuang.dps.service.StudentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * @author x
 *
 */
@Service
public class StudentServiceImpl implements StudentService {

    @Resource
    private StudentRepository studentRepository;

    @Override
    public void save(Student student) {
        studentRepository.save(student);
    }

    @Override
    public Student getStudentById(Long id) {
        return studentRepository.findById(id).get();
    }

    @Override
    public void updateStudent(Student student) {
        studentRepository.save(student);
    }

    @Override
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
}

