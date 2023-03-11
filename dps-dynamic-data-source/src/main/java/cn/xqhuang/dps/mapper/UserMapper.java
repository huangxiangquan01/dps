package cn.xqhuang.dps.mapper;

import cn.xqhuang.dps.entity.User;

import java.util.List;

/**
 * @author huangxq
 * @description: TODO
 * @date 2023/3/716:37
 */
public interface UserMapper {

    List<User> findUsers();
}
