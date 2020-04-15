package com.hwsafe.template.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OracleTableMessageMapper {

	Map<String, String> queryTable(@Param("tableName") String tableName);

	List<Map<String, String>> queryColumns(@Param("tableName") String tableName);

	/**
	 * @param tableName
	 * @return 仅对oracle
	 */
	List<Map<String, String>> queryColumnsComment(@Param("tableName") String tableName);

	/**
	 * @return 获取所有表名
	 */
	List<String> queryOracleTables();

}
