package cn.xqhuang.dps.repository;

import cn.xqhuang.dps.entity.Student;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends ElasticsearchRepository<Student, Long> {

}

