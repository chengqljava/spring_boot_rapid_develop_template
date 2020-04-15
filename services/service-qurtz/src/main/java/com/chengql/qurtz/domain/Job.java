package com.hwsafe.qurtz.domain;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 定时任务
 */
@Data
@TableName(value = "job")
public class Job implements Serializable {

    private static final long serialVersionUID = 1L;

    // 任务id
    @TableId
    private String id;
    // spring bean名称
    private String beanName;
    // 方法名
    private String methodName;
    // 参数
    private String params;
    // cron表达式
    private String cronExpression;
    // 任务状态 0：正常 1：暂停
    private Integer status;
    // 备注
    private String remark;
    // 创建时间
    private Date createTime;
    /**
     * 项目名称
     */
    private String appliactionName;
    /**
     * 计划开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date jobStartTime;
    /**
     * 计划结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date jobEndTime;
    /**
     * 计划一类key
     */
    private String jobPlanKey;
}
