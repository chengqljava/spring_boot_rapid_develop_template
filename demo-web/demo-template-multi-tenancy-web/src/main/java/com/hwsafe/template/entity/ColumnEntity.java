/**
 * 文件名：TableColumnEntity.java
 *
 * 版本信息：
 * 日期：2020年3月31日
 * Copyright 河南汉威电子股份有限公司软件部 Corporation 2020 
 * 版权所有
 *
 */
package com.hwsafe.template.entity;

import org.apache.commons.lang3.StringUtils;

import lombok.Data;

/**
 * 
 * @version 表列名信息
 * 
 */
@Data
public class ColumnEntity {
    /**
     * 列名
     */
    private String columnName;
    /**
     * 数据类型
     */
    private String dataType;
    /**
     * 长度    
     */
    private String dataLength;
    /**
     *  精度
     */
    private Integer dataPrecision;
    /**
     * 小数点后位数
     */
    private Integer dataScale;
    /**
     * 是否允许为空
     */
    private String nullAble;
    /**
     * 列ID
     */
    private int columnId;
    /**
     *  默认值长度
     */
    private String defaultLength;
    /**
     * 默认值
     */
    private String dataDefault;
    /**
     * 列最大程度，用字符串表示 char_Length
     */
    private String charLength;
    /**
     * 注释
     */
    private String comments;

    public String getComments() {
        if (StringUtils.isNoneBlank(comments)) {
            return comments.replace(" ", "").replace("\n", "").replace("\t", "");
        }
        return comments;
    }
}
