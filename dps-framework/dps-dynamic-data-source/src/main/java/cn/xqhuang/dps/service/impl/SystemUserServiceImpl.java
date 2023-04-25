package cn.xqhuang.dps.service.impl;

import cn.xqhuang.dps.annotation.DS;
import cn.xqhuang.dps.entity.SystemUsers;
import cn.xqhuang.dps.mapper.SystemUserMapper;
import cn.xqhuang.dps.service.SystemUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * 后台用户 Service 实现类
 *
 * @author huangxq
 */
@Service("systemUserService")
public class SystemUserServiceImpl implements SystemUserService {

    @Resource
    private SystemUserMapper systemUserMapper;


    @DS("master")
    @Override
    public SystemUsers getUser(Long id) {
        return systemUserMapper.selectById(id);
    }

    @DS("slave")
    @Override
    public SystemUsers getUser2(Long id) {
        return systemUserMapper.selectById(id);
    }
}
