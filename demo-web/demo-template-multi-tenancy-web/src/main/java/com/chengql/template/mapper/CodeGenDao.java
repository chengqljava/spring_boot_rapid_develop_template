package com.hwsafe.template.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.hwsafe.template.entity.OracelColumnAllMessageEntity;
import com.hwsafe.template.entity.OracleColumnComments;
import com.hwsafe.template.entity.OracleTableComments;

@Mapper
public interface CodeGenDao {

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

    void queryOracleAddColumn(@Param("tableName") String tableName, @Param("columnName") String columnName);

    /**
     * 获取表所有列信息
     */
    List<OracelColumnAllMessageEntity> queryColumnsAllMessage(@Param("tableName") String tableName);

    /**
     * 获取主键列名
     */
    String principalColumnName(@Param("tableName") String tableName);

    /**
     * 表名注释
     */
    OracleTableComments tableComments(@Param("tableName") String tableName);

    /**
     * 列名注释
     */
    List<OracleColumnComments> columnComments(@Param("tableName") String tableName);

}
