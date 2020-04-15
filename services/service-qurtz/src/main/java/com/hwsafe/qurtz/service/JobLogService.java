package com.hwsafe.qurtz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.hwsafe.qurtz.domain.JobLog;
import com.hwsafe.qurtz.domain.JobLogQuery;
import com.hwsafe.qurtz.mapper.JobLogMapper;
import com.hwsafe.utils.IDGenerator;

@Service("jobLogService")
@Transactional(readOnly = true)
public class JobLogService extends ServiceImpl<JobLogMapper, JobLog> {

    @Autowired
    private Environment environment;

    public JobLog get(String id) {
        return baseMapper.selectById(id);
    }

    public List<JobLog> list(JobLogQuery jobLogQuery) {
        return baseMapper.list(jobLogQuery);
    }

    public Page<JobLog> listPage(JobLogQuery jobLogQuery) {
        List<JobLog> list = baseMapper.list(jobLogQuery);
        jobLogQuery.setRecords(list);
        return jobLogQuery;
    }

    @Transactional
    public void saveJobLog(JobLog jobLog) {
        jobLog.setId(IDGenerator.OBJECTID.generate());
        jobLog.setAppliactionName(
                environment.getProperty("spring.application.name"));
        super.save(jobLog);
    }

}
