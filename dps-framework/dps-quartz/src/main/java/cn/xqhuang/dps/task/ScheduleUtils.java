package cn.xqhuang.dps.task;


import cn.xqhuang.dps.entity.SysJob;
import org.quartz.*;

/**
 * 定时任务工具类
 * 
 * @author huangxq
 *
 */
public class ScheduleUtils {

    /** 执行目标key */
    public static final String TASK_PROPERTIES = "TASK_PROPERTIES";

    /**
     * 创建定时任务
     */
    public static void createScheduleJob(Scheduler scheduler, SysJob job) {
        try {

            Class<? extends Job> jobClass = QuartzTaskExecution.class;
            // 构建job信息
            Long jobId = job.getId();

            String key = "TASK_KEY_" + jobId;
            JobKey jobKey = JobKey.jobKey(key);
            TriggerKey triggerKey = TriggerKey.triggerKey(key);

            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobKey).build();

            // 表达式调度构建器
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
            cronScheduleBuilder = handleCronScheduleMisfirePolicy(job, cronScheduleBuilder);

            // 按新的cronExpression表达式构建一个新的trigger
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey)
                    .withSchedule(cronScheduleBuilder).build();

            // 放入参数，运行时的方法可以获取
            jobDetail.getJobDataMap().put(TASK_PROPERTIES, job);

            // 判断是否存在
            if (scheduler.checkExists(JobKey.jobKey(key))) {
                // 防止创建时存在数据问题 先移除，然后在执行创建操作
                scheduler.deleteJob(jobKey);
            }

            scheduler.scheduleJob(jobDetail, trigger);

            // 暂停任务
            if (job.getStatus().equals("1")) {
                scheduler.pauseJob(jobKey);
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 设置定时任务策略
     */
    public static CronScheduleBuilder handleCronScheduleMisfirePolicy(SysJob job, CronScheduleBuilder cb)
            throws RuntimeException
    {
        switch (job.getMisfirePolicy())
        {
            case "0":
                return cb;
            case "1":
                return cb.withMisfireHandlingInstructionIgnoreMisfires();
            case "2":
                return cb.withMisfireHandlingInstructionFireAndProceed();
            case "3":
                return cb.withMisfireHandlingInstructionDoNothing();
            default:
                throw new RuntimeException("The task misfire policy '" + job.getMisfirePolicy()
                        + "' cannot be used in cron schedule tasks");
        }
    }
}
