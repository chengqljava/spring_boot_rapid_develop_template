package com.hwsafe.template.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hwsafe.template.mapper.OracleTableMessageMapper;

@Service
public class OracelToMysqlService {
	@Autowired
	private OracleTableMessageMapper oracleTableMessageMapper;

	Map<String, String> queryTable(@Param("tableName") String tableName) {
		return oracleTableMessageMapper.queryTable(tableName);
	}

	List<Map<String, String>> queryColumns(@Param("tableName") String tableName) {
		return oracleTableMessageMapper.queryColumns(tableName);
	}

	/**
	 * @param tableName
	 * @return 仅对oracle
	 */
	List<Map<String, String>> queryColumnsComment(@Param("tableName") String tableName) {
		return oracleTableMessageMapper.queryColumnsComment(tableName);
	}

	/**
	 * @return 获取所有表名
	 */
	List<String> queryOracleTables() {
		return oracleTableMessageMapper.queryOracleTables();
	}
}
