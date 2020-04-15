package com.hwsafe.accept.point.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hwsafe.accept.point.domain.MacHistory;
import com.hwsafe.accept.point.domain.query.MacHistoryQuery;

/**
 * 监测历史表
 * 
 * @author chengql
 * @email chengql@hanwei.cn
 * @date 2019-12-23 17:05:45
 */
public interface MacHistoryMapper extends BaseMapper<MacHistory> {

    List<MacHistory> list(MacHistoryQuery macHistoryQuery);

}
