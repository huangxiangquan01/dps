package cn.xqhuang.dps.controller;

import cn.xqhuang.dps.entity.User;
import cn.xqhuang.dps.model.BaseResult;
import cn.xqhuang.dps.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @author huangxq
 * @description:
 * @date 2023/4/11 14:56 星期二
 */
@RestController
@Api(tags = "用户管理")
public class UserController {

    @Resource
    private UserService userService;

    @ApiModelProperty("创建用户")
    @PostMapping("user")
    public BaseResult<User> createUser(@RequestBody User user){
        userService.createUser(user);
        return new BaseResult.Builder<User>()
                .code(BaseResult.SUCCESS)
                .success(BaseResult.TRUE)
                .traceId(LocalDateTime.now().toString())
                .build();
    }

    @ApiModelProperty("通过id获取用户")
    @GetMapping("user/{id}")
    public BaseResult<User> getUserById(@PathVariable("id") Long id){
        User user = userService.getUserById(id);
        return new BaseResult.Builder<User>()
                .code(BaseResult.SUCCESS)
                .success(BaseResult.TRUE)
                .traceId(LocalDateTime.now().toString())
                .data(user)
                .build();
    }

    @ApiModelProperty("修改用户")
    @PutMapping("user")
    public BaseResult<User> updateUser(@RequestBody User user){
        userService.updateUser(user);
        return new BaseResult.Builder<User>()
                .code(BaseResult.SUCCESS)
                .success(BaseResult.TRUE)
                .traceId(LocalDateTime.now().toString())
                .build();
    }

    @ApiModelProperty("删除用户")
    @DeleteMapping("user/{id}")
    public BaseResult<User> deleteUserById(@PathVariable("id") Long id){
        userService.deleteUserById(id);
        return new BaseResult.Builder<User>()
                .code(BaseResult.SUCCESS)
                .success(BaseResult.TRUE)
                .traceId(LocalDateTime.now().toString())
                .build();
    }
}
