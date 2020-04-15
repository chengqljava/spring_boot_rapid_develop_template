package com.hwsafe.template.controller.domain;

import java.util.List;

import lombok.Data;

@Data
public class TableEntity {

	// 表的名称
	private String tableName;
	// 表的备注
	private String comments;
	// 表的列名(不包含主键)
	private List<ColumnEntity> columns;
}
