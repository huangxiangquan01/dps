package cn.xqhuang.dps.task;

import org.springframework.stereotype.Component;

/**
 * 定时任务调度测试
 * 
 * @author huangxq
 */
@Component("ryTask")
public class RyTask {

    public void ryNoParams()
    {
        System.out.println("执行无参方法");
    }
}
