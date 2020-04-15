package com.hwsafe.qurtz.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.quartz.JobExecutionContext;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.google.gson.Gson;
import com.hwsafe.common.SpringContextHolder;
import com.hwsafe.qurtz.JobConstants;
import com.hwsafe.qurtz.domain.Job;
import com.hwsafe.qurtz.domain.JobLog;
import com.hwsafe.qurtz.service.JobLogService;
import com.hwsafe.utils.IDGenerator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ScheduleJob extends QuartzJobBean {

    ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(
            3,
            new BasicThreadFactory.Builder()
                    .namingPattern("quartz-schedule-pool-%d").daemon(true)
                    .build());

    @Override
    protected void executeInternal(JobExecutionContext context) {

        String jsonJob = context.getMergedJobDataMap()
                .getString(JobConstants.JOB_PARAM_KEY);
        Job jobDO = new Gson().fromJson(jsonJob, Job.class);

        JobLogService jobLogService = SpringContextHolder
                .getBean(JobLogService.class);
        Environment environment = SpringContextHolder
                .getBean(Environment.class);
        InetAddress inetAddress = null;
        try {
            inetAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException e1) {
            // TODO Auto-generated catch block
        }
        // 数据库保存执行记录
        JobLog jobLogDO = new JobLog();
        jobLogDO.setJobId(jobDO.getId());
        jobLogDO.setBeanName(jobDO.getBeanName());
        jobLogDO.setMethodName(jobDO.getMethodName());
        jobLogDO.setParams(jobDO.getParams());
        jobLogDO.setCreateTime(new Date());
        jobLogDO.setId(IDGenerator.OBJECTID.generate());
        jobLogDO.setAppliactionName(
                environment.getProperty("spring.application.name"));
        if (inetAddress != null) {

            jobLogDO.setIpHostName(inetAddress.getHostAddress() + "  "
                    + inetAddress.getHostName());

        }
        // 任务开始时间
        long startTime = System.currentTimeMillis();

        try {

            // 执行任务
            log.info("任务准备执行，任务ID：" + jobDO.getId());
            ScheduleRunnable task = new ScheduleRunnable(jobDO.getBeanName(),
                    jobDO.getMethodName(), jobDO.getParams());
            Future<?> future = executorService.submit(task);

            future.get();

            // 任务执行总时长
            long times = System.currentTimeMillis() - startTime;
            jobLogDO.setTimes((int) times);
            // 任务状态 0：成功 1：失败
            jobLogDO.setStatus(0);

            log.info("任务执行完毕，任务ID：" + jobLogDO.getJobId() + "  总共耗时：" + times
                    + "毫秒");

        } catch (Exception e) {
            log.error("任务执行失败，任务ID：" + jobLogDO.getJobId(), e);

            // 任务执行总时长
            long times = System.currentTimeMillis() - startTime;
            jobLogDO.setTimes((int) times);

            // 任务状态 0：成功 1：失败
            jobLogDO.setStatus(1);
            jobLogDO.setError(StringUtils.substring(e.toString(), 0, 2000));
        } finally {
            jobLogService.save(jobLogDO);
        }

    }
}
