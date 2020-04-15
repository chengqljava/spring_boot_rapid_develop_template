package com.hwsafe.qurtz.domain;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 定时任务
 * 
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class JobQuery<T> extends Page<T> {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String beanName;
    /**
     * 项目名称
     */
    private String appliactionName;
    // 状态
    private Integer status;
}
