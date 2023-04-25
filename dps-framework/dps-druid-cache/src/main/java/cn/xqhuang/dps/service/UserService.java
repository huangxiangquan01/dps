package cn.xqhuang.dps.service;

import cn.xqhuang.dps.entity.User;

/**
 * @author huangxq
 * @description:
 * @date 2023/4/11 14:47 星期二
 */
public interface UserService {

    void createUser(User user);

    User getUserById(Long id);

    User updateUser(User user);

    void deleteUserById(Long id);
}
