package com.chengql.position.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chengql.position.domain.PositionTown;
import com.chengql.position.domain.query.PositionTownQuery;

/**
 * 镇数据库
 * 
 * @author chengql
 * @email chengql@hanwei.cn
 * @date 2019-09-10 11:31:05
 * 
 */
public interface PositionTownMapper extends BaseMapper<PositionTown> {

    List<PositionTown> list(PositionTownQuery positionTownQuery);

}
