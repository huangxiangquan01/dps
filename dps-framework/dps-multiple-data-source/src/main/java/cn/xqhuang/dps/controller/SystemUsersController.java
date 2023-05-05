package cn.xqhuang.dps.controller;

import cn.xqhuang.dps.entity.SystemUsers;
import cn.xqhuang.dps.model.BaseResult;
import cn.xqhuang.dps.service.SystemUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
@RestController
@RequestMapping("/system/user")
public class SystemUsersController {

    @Resource
    private SystemUserService userService;

    @GetMapping("/{id}")
    public BaseResult<SystemUsers> getUser2(@PathVariable("id") Long id) {
        SystemUsers user = userService.getUser(id);
        return new BaseResult.Builder<SystemUsers>()
                .code(BaseResult.SUCCESS)
                .data(user)
                .success(BaseResult.TRUE)
                .build();
    }
}
