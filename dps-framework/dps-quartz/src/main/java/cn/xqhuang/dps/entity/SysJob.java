package cn.xqhuang.dps.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 定时任务调度表 sys_job
 * 
 * @author huangxq
 */
@Getter
@Setter
@TableName("sys_job")
public class SysJob implements Serializable {

    private static final long serialVersionUID = 111111L;

    /** 任务ID */
    @TableId
    private Long id;

    /** 任务名称 */
    private String jobName;

    /** 任务组名 */
    private String jobGroup;

    /** 调用目标字符串 */
    private String invokeTarget;

    /** cron执行表达式 */
    private String cronExpression;

    /** cron计划策略 */
    private String misfirePolicy = "0";

    /** 是否并发执行（0允许 1禁止） */
    private String concurrent;

    /** 任务状态（0正常 1暂停） */
    private String status;

    /** 创建者 */
    private String createBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新者 */
    private String updateBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /** 备注 */
    private String remark;
}
