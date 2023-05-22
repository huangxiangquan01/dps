package cn.xqhuang.dps.controller;

import cn.xqhuang.dps.service.LogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 描述
 *
 * @author xiangquan
 * @date 星期五, 5月 19, 2023, 08:29
 **/
@RestController
public class LogController {

    @Resource
    private LogService logService;

    @GetMapping("/get")
    public String getLog() {
        logService.writeLog();
        return "success";
    }
}
