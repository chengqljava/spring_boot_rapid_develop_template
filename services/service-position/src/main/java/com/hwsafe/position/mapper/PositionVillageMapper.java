package com.hwsafe.position.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hwsafe.position.domain.PositionVillage;
import com.hwsafe.position.domain.query.PositionVillageQuery;

/**
 * 省市县镇村数据
 * 
 * @author chengql
 * @email chengql@hanwei.cn
 * @date 2019-09-10 11:31:05
 * 
 */
public interface PositionVillageMapper extends BaseMapper<PositionVillage> {

    List<PositionVillage> list(PositionVillageQuery positionVillageQuery);

}
