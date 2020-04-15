package com.hwsafe.accept.point.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hwsafe.accept.point.domain.MacHistory5m;
import com.hwsafe.accept.point.domain.query.MacHistory5mQuery;

/**
 * 探头监测历史5分钟数据
 * 
 * @author chengql
 * @email chengql@hanwei.cn
 * @date 2019-12-23 17:05:45
 */
public interface MacHistory5mMapper extends BaseMapper<MacHistory5m> {

    List<MacHistory5m> list(MacHistory5mQuery macHistory5mQuery);

}
