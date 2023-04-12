package cn.xqhuang.dps.service.impl;

import cn.xqhuang.dps.entity.User;
import cn.xqhuang.dps.mapper.UserMapper;
import cn.xqhuang.dps.service.UserService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author huangxq
 * @description:
 * @date 2023/4/11 14:47 星期二
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public void createUser(User user) {
        userMapper.insert(user);
    }

    @Cacheable(value = "user_cache", unless = "#result == null")
    public User getUserById(Long id) {
        return userMapper.getUserById(id);
    }

    @CachePut(value = "user_cache", key = "#user.id", unless = "#result == null")
    public User updateUser(User user) {
        userMapper.updateUser(user);
        return user;
    }

    @CacheEvict(value = "user_cache", key = "#id")
    public void deleteUserById(Long id) {
        userMapper.deleteUserById(id);
    }
}
