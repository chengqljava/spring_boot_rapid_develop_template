package com.chengql.qurtz.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chengql.qurtz.domain.Job;
import com.chengql.qurtz.domain.JobQuery;

/**
 * 定时任务
 */

public interface JobMapper extends BaseMapper<Job> {

    List<Job> list(JobQuery jobQuery);

    List<Job> listByTime(@Param("jobPlanKey") String jobPlanKey,
            @Param("dayTime") String dayTime);
}
