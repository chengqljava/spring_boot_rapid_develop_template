/**
 * 文件名：OracelColumnAllMessageEntity.java
 *
 * 版本信息：
 * 日期：2020年3月31日
 * Copyright 河南汉威电子股份有限公司软件部 Corporation 2020 
 * 版权所有
 *
 */
package com.hwsafe.template.entity;

import lombok.Data;

/**
 * oracle列所有信息
 * 
 * @version
 * 
 */
@Data
public class OracelColumnAllMessageEntity {
    /**
     * 表、视图或Clusters名称
     */
    private String tableName;
    /**
     * 列名
     */
    private String columnName;
    /**
     * 数据类型
     */
    private String dataType;
    /**
     * DATA_TYPE_MOD
     */
    private String dataTypeMod;
    /**
     * DATA_TYPE_OWNER           
     */
    private String dataTypeOwner;
    /**
     * 长度    
     */
    private Integer dataLength;
    /**
     *  精度
     */
    private Integer dataPrecision;
    /**
     * 小数点后位数
     */
    private String dataScale;
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
}
