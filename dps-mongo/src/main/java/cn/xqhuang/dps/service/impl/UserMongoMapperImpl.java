package cn.xqhuang.dps.service.impl;

import cn.xqhuang.dps.entity.User;
import cn.xqhuang.dps.service.UserMongoMapper;
import com.anwen.mongo.mapper.MongoMapperImpl;
import org.springframework.stereotype.Service;

/**
 * 用户mongo映射器
 *
 * @author huangxiangquan@yintatech.com
 * @date 2025/01/07 19:45
 */
@Service
public class UserMongoMapperImpl extends MongoMapperImpl<User> implements UserMongoMapper {

}
