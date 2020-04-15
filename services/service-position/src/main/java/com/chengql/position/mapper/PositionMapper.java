package com.hwsafe.position.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hwsafe.position.domain.Position;
import com.hwsafe.position.domain.query.PositionQuery;

/**
 * 省市县镇村数据
 * 
 * @author chengql
 * @email chengql@hanwei.cn
 * @date 2019-09-10 11:31:05
 * 
 */
public interface PositionMapper extends BaseMapper<Position> {

    List<Position> list(PositionQuery positionQuery);

}
