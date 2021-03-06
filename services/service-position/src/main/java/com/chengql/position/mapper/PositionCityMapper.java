package com.chengql.position.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chengql.position.domain.PositionCity;
import com.chengql.position.domain.query.PositionCityQuery;

/**
 * 县级市数据库
 * 
 * @author chengql
 * @email chengql@hanwei.cn
 * @date 2019-09-10 11:31:05
 */
public interface PositionCityMapper extends BaseMapper<PositionCity> {

    List<PositionCity> list(PositionCityQuery positionCityQuery);

}
