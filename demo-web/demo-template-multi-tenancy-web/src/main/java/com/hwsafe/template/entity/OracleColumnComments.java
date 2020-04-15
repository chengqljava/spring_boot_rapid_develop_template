/**
 * 文件名：OracleTableComments.java
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
 * oracle 列 说明
 * 
 */
@Data
public class OracleColumnComments {
    private String tableName;
    private String columnName;
    private String comments;

}
