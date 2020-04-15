package com.hwsafe.common;

import java.io.Serializable;

import lombok.Data;

/**
 * 
 * @author 单表指定列名单列统计 com.hwsafe.common.SpecifiesColumnStatistical
 */
@Data
public class SpecifiesColumnStatistical
        implements Serializable, Comparable<SpecifiesColumnStatistical> {

    private static final long serialVersionUID = 1L;
    /**
     * 
     * 指定列名 值
     */
    private String specifiesColumnNameValue;
    /**
     * 统计总值
     */
    private int value;

    @Override
    public int compareTo(SpecifiesColumnStatistical arg0) {
        return arg0.getValue() - this.value;
    }

    /**
     * 显示名称
     */
    private String name;

}
