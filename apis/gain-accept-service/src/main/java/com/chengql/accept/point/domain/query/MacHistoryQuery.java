package com.chengql.accept.point.domain.query;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chengql.accept.point.domain.MacHistory;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 监测历史表
 * 
 * @author chengql
 * @date 2019-12-23 17:05:45
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class MacHistoryQuery extends Page<MacHistory> {

    private static final long serialVersionUID = 1L;

}
