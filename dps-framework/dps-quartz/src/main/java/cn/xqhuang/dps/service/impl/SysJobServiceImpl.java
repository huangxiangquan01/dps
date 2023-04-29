package cn.xqhuang.dps.service.impl;

import cn.xqhuang.dps.entity.SysJob;
import cn.xqhuang.dps.mapper.SysJobMapper;
import cn.xqhuang.dps.service.ISysJobService;
import cn.xqhuang.dps.task.ScheduleUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.quartz.CronExpression;
import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * 定时任务调度信息 服务层
 * 
 * @author huangxq
 */
@Service
public class SysJobServiceImpl implements ISysJobService
{
    @Autowired
    private Scheduler scheduler;

    @Autowired
    private SysJobMapper jobMapper;

    /**
     * 获取quartz调度器的计划任务列表
     * 
     * @param job 调度信息
     * @return
     */
    @Override
    public List<SysJob> selectJobList(SysJob job)
    {
        LambdaQueryWrapper<SysJob> wrapper = new LambdaQueryWrapper<>();
        return jobMapper.selectList(wrapper);
    }

    /**
     * 通过调度任务ID查询调度信息
     * 
     * @param jobId 调度任务ID
     * @return 调度任务对象信息
     */
    @Override
    public SysJob selectJobById(Long jobId)
    {
        return jobMapper.selectById(jobId);
    }

    /**
     * 暂停任务
     * 
     * @param job 调度信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int pauseJob(SysJob job)
    {
        Long jobId = job.getId();
        job.setStatus("1");
        int rows = jobMapper.updateById(job);
        if (rows > 0)
        {
            try {
                scheduler.pauseJob(JobKey.jobKey("TASK_KEY_"+ jobId));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return rows;
    }

    /**
     * 恢复任务
     * 
     * @param job 调度信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int resumeJob(SysJob job)
    {
        Long jobId = job.getId();
        job.setStatus("1");
        int rows = jobMapper.updateById(job);
        if (rows > 0)
        {
            try {
                scheduler.resumeJob(JobKey.jobKey("TASK_KEY_"+ jobId));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return rows;
    }

    /**
     * 删除任务后，所对应的trigger也将被删除
     * 
     * @param job 调度信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteJob(SysJob job)
    {
        Long jobId = job.getId();
        int rows = jobMapper.deleteById(jobId);
        if (rows > 0)
        {
            try {
                scheduler.deleteJob(JobKey.jobKey("TASK_KEY_"+ jobId));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return rows;
    }

    /**
     * 批量删除调度信息
     * 
     * @param jobIds 需要删除的任务ID
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteJobByIds(Long[] jobIds)
    {
        jobMapper.deleteBatchIds(Arrays.asList(jobIds));
    }

    /**
     * 任务调度状态修改
     * 
     * @param job 调度信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int changeStatus(SysJob job)
    {
        int rows = 0;
        String status = job.getStatus();
        try {
            if ("0".equals(status))
            {
                scheduler.resumeJob(JobKey.jobKey("TASK_KEY_"+ job.getId()));
            }
            else if ("1".equals(status))
            {
                scheduler.pauseJob(JobKey.jobKey("TASK_KEY_"+ job.getId()));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return rows;
    }

    /**
     * 立即运行任务
     * 
     * @param job 调度信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean run(SysJob job)
    {
        boolean result = false;
        Long jobId = job.getId();
        String jobGroup = job.getJobGroup();
        SysJob properties = selectJobById(job.getId());
        // 参数
        JobDataMap dataMap = new JobDataMap();
        dataMap.put(ScheduleUtils.TASK_PROPERTIES, properties);
        JobKey jobKey = JobKey.jobKey("TASK_KEY_" + jobId, jobGroup);
        try {
            if (scheduler.checkExists(jobKey))
            {
                result = true;
                scheduler.triggerJob(jobKey, dataMap);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return result;
    }

    /**
     * 新增任务
     * 
     * @param job 调度信息 调度信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertJob(SysJob job) {
        job.setStatus("1");
        int rows = jobMapper.insert(job);
        if (rows > 0)
        {
           ScheduleUtils.createScheduleJob(scheduler, job);
        }
        return rows;
    }

    /**
     * 更新任务的时间表达式
     * 
     * @param job 调度信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateJob(SysJob job) {
        SysJob properties = selectJobById(job.getId());
        int rows = jobMapper.updateById(job);
        if (rows > 0)
        {
            updateSchedulerJob(job, properties.getJobGroup());
        }
        return rows;
    }

    /**
     * 更新任务
     * 
     * @param job 任务对象
     * @param jobGroup 任务组名
     */
    public void updateSchedulerJob(SysJob job, String jobGroup) {
        Long jobId = job.getId();
        // 判断是否存在
        JobKey jobKey = JobKey.jobKey("TASK_KEY_" + jobId);

        try {
            if (scheduler.checkExists(jobKey))
            {
                // 防止创建时存在数据问题 先移除，然后在执行创建操作
                scheduler.deleteJob(jobKey);
            }
            ScheduleUtils.createScheduleJob(scheduler, job);
        } catch (Exception e) {
            throw new RuntimeException("");
        }
    }

    /**
     * 校验cron表达式是否有效
     * 
     * @param cronExpression 表达式
     * @return 结果
     */
    @Override
    public boolean checkCronExpressionIsValid(String cronExpression)
    {
        return CronExpression.isValidExpression(cronExpression);
    }
}
