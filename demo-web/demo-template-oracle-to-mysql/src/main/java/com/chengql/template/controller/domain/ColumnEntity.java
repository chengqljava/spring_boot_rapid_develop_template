package com.hwsafe.template.controller.domain;

import lombok.Data;

@Data
public class ColumnEntity {

	// 列名
	private String columnName;
	// 列名类型
	private String dataType;
	// 列名备注
	private String comments;
	// 属性类型
	private String attrType;
	// auto_increment
	private String extra;

}
