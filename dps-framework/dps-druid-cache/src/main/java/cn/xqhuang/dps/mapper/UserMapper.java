package cn.xqhuang.dps.mapper;

import cn.xqhuang.dps.entity.User;
import org.springframework.stereotype.Repository;

/**
 * @author huangxq
 * @description:
 * @date 2023/4/11 14:48 星期二
 */
@Repository
public interface UserMapper {

    int insert(User user);

    User getUserById(Long id);

    User updateUser(User user);

    void deleteUserById(Long id);
}
