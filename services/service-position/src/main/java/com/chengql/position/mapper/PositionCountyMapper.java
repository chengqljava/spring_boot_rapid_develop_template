package com.chengql.position.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chengql.position.domain.PositionCounty;
import com.chengql.position.domain.query.PositionCountyQuery;

/**
 * 地区市数据库
 * 
 * @author chengql
 * @email chengql@hanwei.cn
 * @date 2019-09-10 11:31:05
 * 
 */
public interface PositionCountyMapper extends BaseMapper<PositionCounty> {

    List<PositionCounty> list(PositionCountyQuery positionCountyQuery);

}
