package com.hwsafe.qurtz.domain;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 定时任务日志
 * 
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class JobLogQuery<T> extends Page<T> {

    private static final long serialVersionUID = 1L;

    private String jobId;

    private String beanName;

}
