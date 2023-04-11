package cn.xqhuang.dps.mapper;

import cn.xqhuang.dps.domain.AuthorsExample;
import cn.xqhuang.dps.entity.Authors;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
@Mapper
public interface AuthorsMapper {

   void insert(Authors authors);

   List<Authors> selectByExample(HashMap<String, Object> param);
    
   List<Authors> streamByExample(HashMap<String, Object> param); //以stream形式从mysql获取数据
}