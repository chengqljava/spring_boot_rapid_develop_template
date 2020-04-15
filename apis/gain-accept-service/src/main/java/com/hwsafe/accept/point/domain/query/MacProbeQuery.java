package com.hwsafe.accept.point.domain.query;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hwsafe.accept.point.domain.MacProbe;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 监测探头信息表
 * 
 * @author chengql
 * @email chengql@hanwei.cn
 * @date 2019-12-24 09:41:01
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class MacProbeQuery extends Page<MacProbe> {

    private static final long serialVersionUID = 1L;

}
