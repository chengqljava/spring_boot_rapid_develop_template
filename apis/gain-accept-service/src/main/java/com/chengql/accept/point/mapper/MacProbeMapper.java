package com.hwsafe.accept.point.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hwsafe.accept.point.domain.MacProbe;
import com.hwsafe.accept.point.domain.query.MacProbeQuery;

/**
 * 监测探头信息表
 * 
 * @author chengql
 * @email chengql@hanwei.cn
 * @date 2019-12-24 09:41:01
 */
public interface MacProbeMapper extends BaseMapper<MacProbe> {

    List<MacProbe> list(MacProbeQuery macProbeQuery);

}
