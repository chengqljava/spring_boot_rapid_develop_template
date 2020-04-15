package com.hwsafe.accept.point.domain.query;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hwsafe.accept.point.domain.MacAlarmFault;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 故障报警表
 * 
 * @author chengql
 * @email chengql@hanwei.cn
 * @date 2019-12-23 17:05:45
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class MacAlarmFaultQuery extends Page<MacAlarmFault> {

    private static final long serialVersionUID = 1L;

}
