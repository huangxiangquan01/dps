package cn.xqhuang.dps.mapper;


import cn.xqhuang.dps.entity.User;
import org.apache.ibatis.annotations.Mapper;

import javax.xml.ws.soap.MTOM;
import java.util.List;

/**
 * @author huangxq
 * @date 2021-12-03 15:53
 */
@Mapper
public interface UserMapper {
    List<User> getAll();

    List<User> streamGetAll();
}
