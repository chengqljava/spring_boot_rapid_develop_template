package com.hwsafe.qurtz.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class ScheduleConfig {
    /**
     * 定时是否自动启动 true自动 false不自动启动
     */
    @Value("${qurtz.auto.start:}")
    private Boolean qurtzAutoStart;

    @Bean
    @ConditionalOnMissingBean(name = "schedulerFactoryBean")
    public SchedulerFactoryBean schedulerFactoryBean(DataSource dataSource) {

        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setDataSource(dataSource);

        // quartz参数
        Properties prop = new Properties();
        prop.put("org.quartz.scheduler.instanceName", "hwSafe-HCS-Scheduler");
        prop.put("org.quartz.scheduler.instanceId", "AUTO");
        // 线程池配置
        prop.put("org.quartz.threadPool.class",
                "org.quartz.simpl.SimpleThreadPool");
        prop.put("org.quartz.threadPool.threadCount", "20");
        prop.put("org.quartz.threadPool.threadPriority", "5");
        // JobStore配置
        prop.put("org.quartz.jobStore.class",
                "org.quartz.impl.jdbcjobstore.JobStoreTX");
        // 集群配置
        prop.put("org.quartz.jobStore.isClustered", "false");// 集群true/false
        prop.put("org.quartz.jobStore.clusterCheckinInterval", "15000");
        prop.put("org.quartz.jobStore.maxMisfiresToHandleAtATime", "1");

        prop.put("org.quartz.jobStore.misfireThreshold", "12000");
        prop.put("org.quartz.jobStore.tablePrefix", "QRTZ_");
        factory.setQuartzProperties(prop);

        factory.setSchedulerName("hwSafe-HCS-Scheduler");
        // 延时启动
        factory.setStartupDelay(30);
        factory.setApplicationContextSchedulerContextKey(
                "applicationContextKey");
        // 可选，QuartzScheduler
        // 启动时更新己存在的Job，这样就不用每次修改targetObject后删除qrtz_job_details表对应记录了
        factory.setOverwriteExistingJobs(true);
        // 设置自动启动，默认为true
        if (qurtzAutoStart == null) {
            factory.setAutoStartup(false);
        } else {
            factory.setAutoStartup(qurtzAutoStart);
        }

        return factory;
    }
}
