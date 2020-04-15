package com.hwsafe.accept.point.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hwsafe.accept.point.domain.MacAlarmFault;
import com.hwsafe.accept.point.domain.query.MacAlarmFaultQuery;

/**
 * 故障报警表
 * 
 * @author chengql
 * @email chengql@hanwei.cn
 * @date 2019-12-23 17:05:45
 */
public interface MacAlarmFaultMapper extends BaseMapper<MacAlarmFault> {

    List<MacAlarmFault> list(MacAlarmFaultQuery macAlarmFaultQuery);

}
