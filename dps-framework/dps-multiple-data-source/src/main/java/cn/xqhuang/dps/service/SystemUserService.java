package cn.xqhuang.dps.service;


import cn.xqhuang.dps.entity.SystemUsers;

import java.util.List;

/**
 * 后台用户 Service 接口
 *
 * @author huangxq
 */
public interface SystemUserService {
    /**
     * 通过用户 ID 查询用户
     *
     * @param id 用户ID
     * @return 用户对象信息
     */
    SystemUsers getUser(Long id);

    void updateListById(List<SystemUsers> users);

}
