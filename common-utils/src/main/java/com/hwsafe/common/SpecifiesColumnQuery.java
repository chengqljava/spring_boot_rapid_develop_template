package com.hwsafe.common;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class SpecifiesColumnQuery implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * 指定列名
     */
    private String specifiesColumn;
    /**
     * 指定列名值
     */
    private List<String> specifiesColumnValues;
    /**
     * 数据是否删除
     */
    private boolean enabled = false;
}
