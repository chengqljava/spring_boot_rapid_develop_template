package com.chengql.qurtz.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chengql.qurtz.domain.JobLog;
import com.chengql.qurtz.domain.JobLogQuery;

/**
 * 定时任务日志
 * 
 */
public interface JobLogMapper extends BaseMapper<JobLog> {

    List<JobLog> list(JobLogQuery jobLogQuery);
}
