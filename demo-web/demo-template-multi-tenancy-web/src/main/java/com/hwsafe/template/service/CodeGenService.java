package com.hwsafe.template.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hwsafe.template.entity.OracelColumnAllMessageEntity;
import com.hwsafe.template.entity.OracleColumnComments;
import com.hwsafe.template.entity.OracleTableComments;
import com.hwsafe.template.mapper.CodeGenDao;

@Service
public class CodeGenService {

    @Autowired
    private CodeGenDao codeGenDao;

    /**
     * @param tableName
     *            单表
     */
    public void generatorCode(String tableName) {
        try {
            // 查询表信息
            Map<String, String> table = codeGenDao.queryTable(tableName);
            // 查询列信息
            List<Map<String, String>> columns = codeGenDao.queryColumns(tableName);
            // oracle 列 注解
            List<Map<String, String>> columnsComment = codeGenDao.queryColumnsComment(tableName);
            // 取值列名与注解
            Map<String, String> columnNameComment = new HashMap<String, String>();
            for (Map<String, String> map : columnsComment) {
                columnNameComment.put(map.get("columnName"), map.get("columnComment"));
            }

            for (Map<String, String> map : columns) {
                map.put("columnComment", columnNameComment.get(map.get("columnName")));
            }

            // 生成代码

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * @param tableNames
     *            多表
     */
    public void generatorCode(List<String> tableNames) {
        for (String tableName : tableNames) {
            generatorCode(tableName);
        }
    }

    /**
     * @param tableNames
     *            多表
     */
    public void generatorCode(String... tableNames) {
        for (String tableName : tableNames) {
            generatorCode(tableName);
        }
    }

    /**
     * 获取表所有列信息
     */
    public List<OracelColumnAllMessageEntity> queryColumnsAllMessage(String tableName) {
        return codeGenDao.queryColumnsAllMessage(tableName);

    }

    /**
     * 获取主键列名
     */
    public String principalColumnName(String tableName) {
        return codeGenDao.principalColumnName(tableName);
    }

    /**
     * 表名注释
     */
    public OracleTableComments tableComments(String tableName) {
        return codeGenDao.tableComments(tableName);
    }

    /**
     * 列名注释
     */
    public List<OracleColumnComments> columnComments(@Param("tableName") String tableName) {
        return codeGenDao.columnComments(tableName);
    }
}
