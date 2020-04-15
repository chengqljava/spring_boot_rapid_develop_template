package com.hwsafe.qurtz.utils;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;

import com.google.gson.Gson;
import com.hwsafe.qurtz.JobConstants;
import com.hwsafe.qurtz.domain.Job;

/**
 * 定时任务工具类
 */
public class ScheduleUtil {

    /**
     * 获取触发器key
     */
    public static TriggerKey getTriggerKey(String jobId) {
        return TriggerKey.triggerKey(JobConstants.JOB_NAME_PREFIX + jobId);
    }

    /**
     * 获取jobKey
     */
    public static JobKey getJobKey(String jobId) {
        return JobKey.jobKey(JobConstants.JOB_NAME_PREFIX + jobId);
    }

    /**
     * 获取表达式触发器
     */
    public static CronTrigger getCronTrigger(Scheduler scheduler,
            String jobId) {
        try {
            return (CronTrigger) scheduler.getTrigger(getTriggerKey(jobId));
        } catch (SchedulerException e) {
            throw new RuntimeException("获取定时任务CronTrigger出现异常", e);
        }
    }

    /**
     * 创建定时任务
     */
    public static void createScheduleJob(Scheduler scheduler, Job scheduleJob) {
        try {
            // 构建job信息
            JobDetail jobDetail = JobBuilder.newJob(ScheduleJob.class)
                    .withIdentity(getJobKey(scheduleJob.getId())).build();

            // 表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder
                    .cronSchedule(scheduleJob.getCronExpression())
                    .withMisfireHandlingInstructionDoNothing();

            // 按新的cronExpression表达式构建一个新的trigger
            /*
             * CronTrigger trigger = TriggerBuilder.newTrigger()
             * .withIdentity(getTriggerKey(scheduleJob.getId())).withSchedule(
             * scheduleBuilder) .build();
             */
            TriggerBuilder triggerBuilder = TriggerBuilder.newTrigger();
            // 设置开始时间结束时间
            if (null != scheduleJob.getJobEndTime()
                    || null != scheduleJob.getJobStartTime()) {
                if (null != scheduleJob.getJobStartTime()) {
                    triggerBuilder.startAt(scheduleJob.getJobStartTime());
                }
                if (null != scheduleJob.getJobEndTime()) {
                    triggerBuilder.endAt(scheduleJob.getJobEndTime());
                }
            }
            CronTrigger trigger = (CronTrigger) triggerBuilder
                    .withIdentity(getTriggerKey(scheduleJob.getId()))
                    .withSchedule(scheduleBuilder).build();
            // 放入参数，运行时的方法可以获取
            jobDetail.getJobDataMap().put(JobConstants.JOB_PARAM_KEY,
                    new Gson().toJson(scheduleJob));

            scheduler.scheduleJob(jobDetail, trigger);

            // 暂停任务
            if (scheduleJob.getStatus() == JobConstants.ScheduleStatus.PAUSE
                    .getValue()) {
                pauseJob(scheduler, scheduleJob.getId());
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
            throw new RuntimeException("创建定时任务失败", e);
        }
    }

    /**
     * 更新定时任务
     */
    public static void updateScheduleJob(Scheduler scheduler, Job scheduleJob) {
        try {
            TriggerKey triggerKey = getTriggerKey(scheduleJob.getId());

            // 表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder
                    .cronSchedule(scheduleJob.getCronExpression())
                    .withMisfireHandlingInstructionDoNothing();

            CronTrigger trigger = getCronTrigger(scheduler,
                    scheduleJob.getId());

            if (null != scheduleJob.getJobEndTime()
                    || null != scheduleJob.getJobStartTime()) {
                if (null != scheduleJob.getJobStartTime()) {
                    trigger.getTriggerBuilder()
                            .startAt(scheduleJob.getJobStartTime());
                }
                if (null != scheduleJob.getJobEndTime()) {
                    trigger.getTriggerBuilder()
                            .endAt(scheduleJob.getJobEndTime());
                }
            }
            // 按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey)
                    .withSchedule(scheduleBuilder).build();

            // 参数
            trigger.getJobDataMap().put(JobConstants.JOB_PARAM_KEY,
                    new Gson().toJson(scheduleJob));

            scheduler.rescheduleJob(triggerKey, trigger);

            // 暂停任务
            if (scheduleJob.getStatus() == JobConstants.ScheduleStatus.PAUSE
                    .getValue()) {
                pauseJob(scheduler, scheduleJob.getId());
            }

        } catch (SchedulerException e) {
            throw new RuntimeException("更新定时任务失败", e);
        }
    }

    /**
     * 立即执行任务
     */
    public static void run(Scheduler scheduler, Job scheduleJob) {
        try {
            // 参数
            JobDataMap dataMap = new JobDataMap();
            dataMap.put(JobConstants.JOB_PARAM_KEY,
                    new Gson().toJson(scheduleJob));

            scheduler.triggerJob(getJobKey(scheduleJob.getId()), dataMap);
        } catch (SchedulerException e) {
            throw new RuntimeException("立即执行定时任务失败", e);
        }
    }

    /**
     * 暂停任务
     */
    public static void pauseJob(Scheduler scheduler, String jobId) {
        try {
            scheduler.pauseJob(getJobKey(jobId));
        } catch (SchedulerException e) {
            throw new RuntimeException("暂停定时任务失败", e);
        }
    }

    /**
     * 恢复任务
     */
    public static void resumeJob(Scheduler scheduler, String jobId) {
        try {
            scheduler.resumeJob(getJobKey(jobId));
        } catch (SchedulerException e) {
            throw new RuntimeException("暂停定时任务失败", e);
        }
    }

    /**
     * 删除定时任务
     */
    public static void deleteScheduleJob(Scheduler scheduler, String jobId) {
        try {
            scheduler.deleteJob(getJobKey(jobId));
        } catch (SchedulerException e) {
            throw new RuntimeException("删除定时任务失败", e);
        }
    }

}
