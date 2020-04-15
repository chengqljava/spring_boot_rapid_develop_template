package com.hwsafe.position.domain.query;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hwsafe.position.domain.PositionTown;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 镇数据库
 * 
 * @author chengql
 * @email chengql@hanwei.cn
 * @date 2019-09-10 11:31:05
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class PositionTownQuery extends Page<PositionTown> {

    private static final long serialVersionUID = 1L;

}
