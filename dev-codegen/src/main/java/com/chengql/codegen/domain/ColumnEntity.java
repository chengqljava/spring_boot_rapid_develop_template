package com.hwsafe.codegen.domain;

import lombok.Data;

@Data
public class ColumnEntity {

    // 列名
    private String columnName;
    // 列名类型
    private String dataType;
    // 列名备注
    private String comments;

    // 属性名称(第一个字母大写)，如：user_name => UserName
    private String attrNameUpper;
    // 属性名称(第一个字母小写)，如：user_name => userName
    private String attrNameLower;
    // 属性类型
    private String attrType;
    // auto_increment
    private String extra;

}
