package com.hwsafe.accept.point.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hwsafe.accept.point.domain.MacHistory1h;
import com.hwsafe.accept.point.domain.query.MacHistory1hQuery;

/**
 * 探头监测历史1小时数据
 * 
 * @author chengql
 * @email chengql@hanwei.cn
 * @date 2019-12-23 17:05:45
 */
public interface MacHistory1hMapper extends BaseMapper<MacHistory1h> {

    List<MacHistory1h> list(MacHistory1hQuery macHistory1hQuery);

}
