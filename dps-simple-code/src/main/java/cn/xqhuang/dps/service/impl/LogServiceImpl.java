package cn.xqhuang.dps.service.impl;

import cn.xqhuang.dps.annotattion.Log;
import cn.xqhuang.dps.service.LogService;
import org.springframework.stereotype.Service;

/**
 * 描述
 *
 * @author xiangquan
 * @date 星期三, 5月 17, 2023, 09:11
 **/
@Service
public class LogServiceImpl implements LogService {
    @Log
    @Override
    public void writeLog() {
        System.out.println("write log");
    }
}
