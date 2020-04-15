package com.chengql.position.domain.query;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chengql.position.domain.PositionCounty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 地区市数据库
 * 
 * @author chengql
 * @email chengql@hanwei.cn
 * @date 2019-09-10 11:31:05
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class PositionCountyQuery extends Page<PositionCounty> {

    private static final long serialVersionUID = 1L;

}
