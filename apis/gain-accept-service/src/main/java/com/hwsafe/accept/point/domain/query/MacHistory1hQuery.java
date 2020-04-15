package com.hwsafe.accept.point.domain.query;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hwsafe.accept.point.domain.MacHistory1h;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 探头监测历史1小时数据
 * 
 * @author chengql
 * @email chengql@hanwei.cn
 * @date 2019-12-23 17:05:45
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class MacHistory1hQuery extends Page<MacHistory1h> {

    private static final long serialVersionUID = 1L;

}
