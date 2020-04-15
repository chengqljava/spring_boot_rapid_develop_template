package com.hwsafe.qurtz.service;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.utils.collection.ListUtil;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hwsafe.qurtz.JobConstants;
import com.hwsafe.qurtz.domain.Job;
import com.hwsafe.qurtz.domain.JobQuery;
import com.hwsafe.qurtz.mapper.JobMapper;
import com.hwsafe.qurtz.utils.ScheduleUtil;
import com.hwsafe.utils.IDGenerator;
import com.hwsafe.utils.StringUtil;

@Service("jobService")
@Transactional(readOnly = true)
public class JobService extends ServiceImpl<JobMapper, Job> {

    @Autowired
    private Scheduler scheduler;
    @Autowired
    private Environment environment;
    /**
     * 定时是否自动启动 true自动 false不自动启动
     */
    @Value("${qurtz.auto.start:}")
    private Boolean qurtzAutoStart;

    /**
     * 项目启动时，初始化定时器
     * 
     */
    @PostConstruct
    public void init() {
        // 启动定时任务进行加载
        if (qurtzAutoStart != null && qurtzAutoStart) {
            try {
                // 清空重新加载
                scheduler.clear();
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
            JobQuery<Job> jobQuery = new JobQuery<Job>();
            jobQuery.setSearchCount(false);
            jobQuery.setSize(-1);
            jobQuery.setStatus(JobConstants.ScheduleStatus.NORMAL.getValue());
            jobQuery.setAppliactionName(
                    environment.getProperty("spring.application.name"));
            List<Job> jobList = baseMapper.list(jobQuery);
            Date date = new Date();
            if (ListUtil.isNotEmpty(jobList)) {
                for (Job job : jobList) {
                    if (null != job.getJobEndTime()
                            && date.getTime() > job.getJobEndTime().getTime()) {
                        continue;
                    }
                    CronTrigger cronTrigger = ScheduleUtil
                            .getCronTrigger(scheduler, job.getId());
                    if (cronTrigger == null) {
                        ScheduleUtil.createScheduleJob(scheduler, job);
                    } else {
                        ScheduleUtil.updateScheduleJob(scheduler, job);
                    }
                }
            }
        }
    }

    public Job get(String id) {
        QueryWrapper<Job> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        return super.getOne(queryWrapper);
    }

    public List<Job> list(JobQuery jobQuery) {
        return baseMapper.list(jobQuery);
    }

    public Page<Job> page(JobQuery<Job> jobQuery) {
        jobQuery.setRecords(baseMapper.list(jobQuery));
        return jobQuery;
    }

    /**
     * 添加任务
     * 
     * @param job
     */
    @Transactional
    public void saveJob(Job job) {
        if (StringUtil.isBlank(job.getId())) {
            job.setId(IDGenerator.OBJECTID.generate());
        }
        job.setCreateTime(new Date());
        job.setAppliactionName(
                environment.getProperty("spring.application.name"));
        job.setStatus(JobConstants.ScheduleStatus.NORMAL.getValue());
        super.save(job);

        ScheduleUtil.createScheduleJob(scheduler, job);
    }

    /**
     * 更新任务
     * 
     * @param job
     */
    @Transactional
    public void updateJob(Job job) {
        super.updateById(job);
        ScheduleUtil.updateScheduleJob(scheduler, job);
    }

    /**
     * 删除任务
     * 
     * @param id
     */
    @Transactional
    public void delete(String id) {
        baseMapper.deleteById(id);
        ScheduleUtil.deleteScheduleJob(scheduler, id);
    }

    /**
     * 执行任务
     * 
     * @param id
     */
    @Transactional
    public void run(String id) {
        ScheduleUtil.run(scheduler, get(id));
    }

    /**
     * 暂停任务
     * 
     * @param id
     */
    @Transactional
    public void pause(String id) {
        ScheduleUtil.pauseJob(scheduler, id);
        Job job = get(id);
        job.setStatus(JobConstants.ScheduleStatus.PAUSE.getValue());
        super.updateById(job);
    }

    /**
     * 恢复设置
     * 
     * @param id
     */
    @Transactional
    public void resume(String id) {
        ScheduleUtil.resumeJob(scheduler, id);

        Job job = get(id);
        job.setStatus(JobConstants.ScheduleStatus.NORMAL.getValue());
        super.updateById(job);

    }

    @Transactional
    public void deleteByIds(List<String> ids) {
        for (int i = 0; i < ids.size(); i++) {
            delete(ids.get(i));
        }
    }

    public List<Job> getByTime(String jobPlanKey, String dayTime) {

        return baseMapper.listByTime(jobPlanKey, dayTime);
    }
}
