package cn.xqhuang.dps.controller;

import cn.xqhuang.dps.entity.SysUser;
import cn.xqhuang.dps.service.SysUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author huangxq
 * @description:
 * @date 2023/4/24 11:43 星期一
 */
@RestController
public class SysUserController {

    @Resource
    private SysUserService sysUserService;


    @GetMapping("sysUser")
    public SysUser getSysUser() {
        return sysUserService.getById1(1L);
    }
}
