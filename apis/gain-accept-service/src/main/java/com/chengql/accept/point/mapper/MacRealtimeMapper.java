package com.chengql.accept.point.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chengql.accept.point.domain.MacRealtime;
import com.chengql.accept.point.domain.query.MacRealtimeQuery;

/**
 * 实时监测表
 * 
 * @author chengql
 * @email chengql@hanwei.cn
 * @date 2019-12-23 17:30:45
 */
public interface MacRealtimeMapper extends BaseMapper<MacRealtime> {

    List<MacRealtime> list(MacRealtimeQuery macRealtimeQuery);

}
