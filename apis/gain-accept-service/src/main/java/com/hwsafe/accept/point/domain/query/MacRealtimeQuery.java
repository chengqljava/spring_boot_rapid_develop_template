package com.hwsafe.accept.point.domain.query;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hwsafe.accept.point.domain.MacRealtime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 实时监测表
 * 
 * @author chengql
 * @email chengql@hanwei.cn
 * @date 2019-12-23 17:30:45
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class MacRealtimeQuery extends Page<MacRealtime> {

    private static final long serialVersionUID = 1L;

}
