package cn.xqhuang.dps.controller;

import cn.xqhuang.dps.entity.SysJobLog;
import cn.xqhuang.dps.model.BaseResult;
import cn.xqhuang.dps.service.ISysJobLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 调度日志操作处理
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/monitor/jobLog")
public class SysJobLogController {
    @Autowired
    private ISysJobLogService jobLogService;


    /**
     * 根据调度编号获取详细信息
     */
    @GetMapping(value = "/{jobLogId}")
    public BaseResult<SysJobLog> getInfo(@PathVariable Long jobLogId)
    {
        return new BaseResult.Builder<SysJobLog>()
                .data(jobLogService.selectJobLogById(jobLogId))
                .build();
    }

}
