package cn.xqhuang.dps.controller;

import cn.xqhuang.dps.annotation.AccessLimit;
import cn.xqhuang.dps.constans.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pass")
@AccessLimit(second = 13,maxTime = 5,forbiddenTime = 50)
@Slf4j
public class PassController {

    @GetMapping("/getOne/{id}")
    public Result getOne(@PathVariable("id") Integer id){
        log.info("执行[pass]-getOne()方法，id为{}", id);
        return Result.SUCCESS();
    }


    @GetMapping("/get")
//    表示此接口 3 秒内最大访问次数为 2，禁用时长为 40 秒
    @AccessLimit(second = 3, maxTime = 2, forbiddenTime = 40L)
    public Result get(){
        log.info("执行【pass】-get()方法");
        return Result.SUCCESS();
    }
    @PostMapping("/post")
    //    表示此接口 20 秒内最大访问次数为 5，禁用时长为 60 秒
    @AccessLimit(second = 20, maxTime = 5, forbiddenTime = 60L)
    public Result post(){
        log.info("执行【pass】-post()方法");
        return Result.SUCCESS();
    }

    @PutMapping("/put")
    public Result put(){
        log.info("执行【pass】-put()方法");
        return Result.SUCCESS();
    }

    @DeleteMapping("/delete")
    public Result delete(){
        log.info("执行【pass】-delete()方法");
        return Result.SUCCESS();
    }
}