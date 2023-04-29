package cn.xqhuang.dps.task;

import cn.xqhuang.dps.entity.SysJob;
import org.apache.commons.lang3.StringUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Method;

/**
 * 定时任务处理（禁止并发执行）
 * 
 * @author huangxq
 *
 */
public class QuartzTaskExecution implements Job {

    protected ApplicationContext getApplicationContext(JobExecutionContext context) {
        ApplicationContext appCtx = null;
        try {
            appCtx = (ApplicationContext) context.getScheduler().getContext()
                    .get("applicationContextKey");
        } catch (SchedulerException e) {
            throw new RuntimeException("获取appCtx错误", e);
        }
        return appCtx;
    }

    @Override
    public void execute(JobExecutionContext context)  {
        SysJob sysJob = (SysJob) context.getMergedJobDataMap().get(ScheduleUtils.TASK_PROPERTIES);
        String invokeTarget = sysJob.getInvokeTarget();

        String beanName = getBeanName(invokeTarget);
        String methodName = getMethodName(invokeTarget);
        // List<Object[]> methodParams = getMethodParams(invokeTarget);
        try {
            Object bean = context.getScheduler().getContext().get(beanName);
            Method method = bean.getClass().getMethod(methodName);
            method.invoke(bean);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 获取bean名称
     *
     * @param invokeTarget 目标字符串
     * @return bean名称
     */
    public static String getBeanName(String invokeTarget)
    {
        String beanName = StringUtils.substringBefore(invokeTarget, "(");
        return StringUtils.substringBeforeLast(beanName, ".");
    }

    /**
     * 获取bean方法
     *
     * @param invokeTarget 目标字符串
     * @return method方法
     */
    public static String getMethodName(String invokeTarget)
    {
        String methodName = StringUtils.substringBefore(invokeTarget, "(");
        return StringUtils.substringAfterLast(methodName, ".");
    }
}
