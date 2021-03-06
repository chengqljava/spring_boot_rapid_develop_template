package com.chengql.accept.point.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chengql.accept.point.domain.MacAlarmFault;
import com.chengql.accept.point.domain.query.MacAlarmFaultQuery;

/**
 * 故障报警表
 * 
 * @author chengql
 * @date 2019-12-23 17:05:45
 */
public interface MacAlarmFaultMapper extends BaseMapper<MacAlarmFault> {

    List<MacAlarmFault> list(MacAlarmFaultQuery macAlarmFaultQuery);

}
