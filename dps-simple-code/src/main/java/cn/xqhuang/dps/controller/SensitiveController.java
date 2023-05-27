package cn.xqhuang.dps.controller;

import cn.xqhuang.dps.filter.SensitiveFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class SensitiveController {

    @Autowired
    SensitiveFilter sensitiveFilter;


    @GetMapping("/sensitive")
    public String sensitive(String keyword){
        return sensitiveFilter.replaceSensitiveWord(keyword);
    }


}