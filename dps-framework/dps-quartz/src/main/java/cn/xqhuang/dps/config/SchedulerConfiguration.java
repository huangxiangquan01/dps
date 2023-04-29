package cn.xqhuang.dps.config;

import cn.xqhuang.dps.entity.SysJob;
import cn.xqhuang.dps.service.ISysJobService;
import cn.xqhuang.dps.task.RyTask;
import cn.xqhuang.dps.task.ScheduleUtils;
import org.quartz.Scheduler;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * 描述
 *
 * @author xiangquan
 * @date 星期五, 4月 28, 2023, 10:04
 **/
@Configuration
public class SchedulerConfiguration {

    @Resource
    private Scheduler scheduler;

    @Resource
    private ISysJobService iSysJobService;

    @Resource
    private RyTask ryTask;

    /**
     * 项目启动时，初始化定时器 主要是防止手动修改数据库导致未同步到定时任务处理（注：不能手动修改数据库ID和任务组名，否则会导致脏数据）
     */
    @PostConstruct
    public void init()
    {
        try {
            scheduler.clear();
            List<SysJob> jobList = iSysJobService.selectJobList(null);
            for (SysJob job : jobList)
            {
                 ScheduleUtils.createScheduleJob(scheduler, job);
            }

            // 执行器关联调度方法
            scheduler.getContext().put("ryTask", ryTask);
        } catch (Exception e) {
            throw new RuntimeException("quartz init fail");
        }

    }
}
