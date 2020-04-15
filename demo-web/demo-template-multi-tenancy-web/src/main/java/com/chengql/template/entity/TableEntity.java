/**
 * 文件名：TableEntity.java
 *
 * 版本信息：
 * 日期：2020年3月31日
 * Copyright 河南汉威电子股份有限公司软件部 Corporation 2020 
 * 版权所有
 *
 */
package com.hwsafe.template.entity;

import java.util.List;

import lombok.Data;

/**
 * 
 * @version 表信息
 */
@Data
public class TableEntity {
    /**
     * 表名
     */
    private String tableName;
    /**
     * 列信息
     */
    private List<ColumnEntity> columnEntityList;
    /**
     * 注释
     */

    private String commonts;

    /**
     * 表的主键
     */
    private ColumnEntity pk;

}
