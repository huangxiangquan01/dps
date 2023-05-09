package cn.xqhuang.dps.controller;

import cn.xqhuang.dps.entity.User;
import cn.xqhuang.dps.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author huangxq
 * @date 2021-12-03 15:53
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/getAll")
    public List<User> getAll(){
        return userService.getAll();
    }

    @GetMapping("/exportUser")
    public void exportUser(HttpServletResponse response){
        userService.exportUser(response);
    }

    @GetMapping("/streamExportUser")
    public void streamExportUser(HttpServletResponse response){
        userService.streamExportUser(response);
    }

    @GetMapping("/streamExportUserMoreSheet")
    public void streamExportUserMoreSheet(HttpServletResponse response){
        userService.streamExportUserMoreSheet(response);
    }
}
