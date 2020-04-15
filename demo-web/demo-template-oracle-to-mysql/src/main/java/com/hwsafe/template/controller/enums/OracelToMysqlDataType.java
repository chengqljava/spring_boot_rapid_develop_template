package com.hwsafe.template.controller.enums;

/**
 * @author chengql oracel 数据 类型转化 mysql
 *
 */
public enum OracelToMysqlDataType {
	NUMBER_INTEGER("NUMBER", "INTEGER");
	private String oracleDataType;
	private String mysqlDataType;

	private OracelToMysqlDataType(String oracleDataType, String mysqlDataType) {
		this.oracleDataType = oracleDataType;
		this.mysqlDataType = mysqlDataType;
	}
}
