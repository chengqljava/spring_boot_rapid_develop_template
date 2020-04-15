package com.hwsafe.position.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hwsafe.position.domain.PositionProvice;
import com.hwsafe.position.domain.query.PositionProviceQuery;

/**
 * 省份数据库
 * 
 * @author chengql
 * @email chengql@hanwei.cn
 * @date 2019-09-10 11:31:05
 * 
 */
public interface PositionProviceMapper extends BaseMapper<PositionProvice> {

    List<PositionProvice> list(PositionProviceQuery positionProviceQuery);

}
