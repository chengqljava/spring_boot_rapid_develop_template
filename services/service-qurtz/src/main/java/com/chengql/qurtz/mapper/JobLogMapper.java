package com.hwsafe.qurtz.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hwsafe.qurtz.domain.JobLog;
import com.hwsafe.qurtz.domain.JobLogQuery;

/**
 * 定时任务日志
 * 
 */
public interface JobLogMapper extends BaseMapper<JobLog> {

    List<JobLog> list(JobLogQuery jobLogQuery);
}
