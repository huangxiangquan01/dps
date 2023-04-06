package cn.xqhuang.dps.mapper;

import cn.xqhuang.dps.domain.AuthorsExample;
import cn.xqhuang.dps.entity.Authors;

import java.util.HashMap;
import java.util.List;

public interface AuthorsMapper {

   List<Authors> selectByExample(HashMap<String, Object> param);
    
   List<Authors> streamByExample(HashMap<String, Object> param); //以stream形式从mysql获取数据
}