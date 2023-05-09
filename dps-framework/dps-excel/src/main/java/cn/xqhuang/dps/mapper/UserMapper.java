package cn.xqhuang.dps.mapper;


import cn.xqhuang.dps.entity.User;

import java.util.List;

/**
 * @author huangxq
 * @date 2021-12-03 15:53
 */
public interface UserMapper {
    List<User> getAll();

    List<User> streamGetAll();
}
