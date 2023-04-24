package cn.xqhuang.dps.service.impl;

import cn.xqhuang.dps.annotation.DB;
import cn.xqhuang.dps.entity.SysUser;
import cn.xqhuang.dps.mapper.SysUserMapper;
import cn.xqhuang.dps.service.SysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author huangxq
 * @description:
 * @date 2023/4/24 11:40 星期一
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser getById1(Long id) {
        return sysUserMapper.selectById(id);
    }

    @Override
    @DB("db2")
    public SysUser getById2(Long id) {
        return sysUserMapper.selectById(id);
    }
}
