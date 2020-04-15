package com.hwsafe.codegen.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CodeGenDao {

    Map<String, String> queryTable(@Param("tableName") String tableName);

    List<Map<String, String>> queryColumns(
            @Param("tableName") String tableName);

    /**
     * @param tableName
     * @return 仅对oracle
     */
    List<Map<String, String>> queryColumnsComment(
            @Param("tableName") String tableName);

    /**
     * @return 获取所有表名
     */
    List<String> queryOracleTables();

    void queryOracleAddColumn(@Param("tableName") String tableName,
            @Param("columnName") String columnName);

}
