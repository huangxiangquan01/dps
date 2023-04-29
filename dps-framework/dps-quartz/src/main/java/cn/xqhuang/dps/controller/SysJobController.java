package cn.xqhuang.dps.controller;

import cn.xqhuang.dps.entity.SysJob;
import cn.xqhuang.dps.model.BaseResult;
import cn.xqhuang.dps.service.ISysJobService;
import org.quartz.SchedulerException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 调度任务信息操作处理
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/monitor/job")
public class SysJobController {

    @Resource
    private ISysJobService jobService;

    /**
     * 获取定时任务详细信息
     */
    @GetMapping(value = "/{jobId}")
    public BaseResult<SysJob> getInfo(@PathVariable("jobId") Long jobId)
    {
        return new BaseResult.Builder<SysJob>()
                .data(jobService.selectJobById(jobId))
                .build();
    }

    /**
     * 新增定时任务
     */
    @PostMapping
    public BaseResult<Integer> add(@RequestBody SysJob job) {
        return new BaseResult.Builder<Integer>()
                .code(BaseResult.SUCCESS)
                .data(jobService.insertJob(job))
                .build();
    }

    /**
     * 修改定时任务
     */
    @PutMapping
    public BaseResult<Integer> edit(@RequestBody SysJob job)
    {
        // job.setUpdateBy(getUsername());

        return new BaseResult.Builder<Integer>()
                .code(BaseResult.SUCCESS)
                .data(jobService.updateJob(job))
                .build();
    }

    /**
     * 定时任务状态修改
     */
    @PutMapping("/changeStatus")
    public BaseResult<Integer> changeStatus(@RequestBody SysJob job) throws SchedulerException
    {
        SysJob newJob = jobService.selectJobById(job.getId());
        newJob.setStatus(job.getStatus());
        return new BaseResult.Builder<Integer>()
                .code(BaseResult.SUCCESS)
                .data(jobService.changeStatus(newJob))
                .build();
    }

    /**
     * 定时任务立即执行一次
     */
    @PutMapping("/run")
    public BaseResult<Boolean> run(@RequestBody SysJob job) throws SchedulerException
    {
        boolean result = jobService.run(job);
        return new BaseResult.Builder<Boolean>()
                .code(BaseResult.SUCCESS)
                .data(result)
                .build();
    }

    /**
     * 删除定时任务
     */
    @DeleteMapping("/{jobIds}")
    public BaseResult<Integer> remove(@PathVariable Long[] jobIds)
    {
        jobService.deleteJobByIds(jobIds);
        return new BaseResult.Builder<Integer>()
                .code(BaseResult.SUCCESS)
                .build();
    }
}
