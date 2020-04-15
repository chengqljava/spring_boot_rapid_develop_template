package com.hwsafe.position.domain.query;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hwsafe.position.domain.Position;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 省市县镇村数据
 * 
 * @author chengql
 * @email chengql@hanwei.cn
 * @date 2019-09-10 11:31:05
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class PositionQuery extends Page<Position> {

    private static final long serialVersionUID = 1L;

}
