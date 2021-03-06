package com.chengql.accept.point.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chengql.accept.point.domain.MacAlarmHandle;
import com.chengql.accept.point.domain.query.MacAlarmHandleQuery;

/**
 * 监测报警信息处理表
 * 
 * @author chengql
 * @email chengql@hanwei.cn
 * @date 2019-12-23 17:05:45
 */
public interface MacAlarmHandleMapper extends BaseMapper<MacAlarmHandle> {

    List<MacAlarmHandle> list(MacAlarmHandleQuery macAlarmHandleQuery);

}
