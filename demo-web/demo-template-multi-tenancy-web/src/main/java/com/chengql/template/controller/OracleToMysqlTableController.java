/**
 * 文件名：OracleToMyslqTableController.java
 *
 * 版本信息：
 * 日期：2020年3月31日
 * Copyright 河南汉威电子股份有限公司软件部 Corporation 2020 
 * 版权所有
 *
 */
package com.hwsafe.template.controller;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.ext.spring.BeetlGroupUtilConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hwsafe.template.entity.ColumnEntity;
import com.hwsafe.template.entity.OracelColumnAllMessageEntity;
import com.hwsafe.template.entity.OracleColumnComments;
import com.hwsafe.template.entity.OracleTableComments;
import com.hwsafe.template.entity.TableEntity;
import com.hwsafe.template.enums.JavaToMysqlDataTypeEnum;
import com.hwsafe.template.enums.OracleToMysqlDataTypeEnum;
import com.hwsafe.template.initOver.InitOver;
import com.hwsafe.template.service.CodeGenService;
import com.hwsafe.template.util.ObjectUtil;
import com.hwsafe.utils.BeanMapper;

/**
 * 
 * Oracle转化为mysql
 * 
 */
@RestController
public class OracleToMysqlTableController {
    @Autowired
    private BeetlGroupUtilConfiguration beetlGroupUtilConfiguration;
    @Autowired
    private CodeGenService codeGenService;
    @Autowired
    private InitOver initOver;

    /**
     * 
     * 全部转化
     */
    @RequestMapping(value = "/oracleTomysql/all")
    public String oracleTomysqlAll() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        oracleToMysql(null);
        return "String";
    }

    /**
     *
     * 指定表名转化
     */
    @RequestMapping(value = "/oracleTomysql/{tableNames}")
    public String tableNames(@PathVariable("tableNames") String tableNames) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        Set<String> set = new HashSet<>();
        for (String tableName : tableNames.split(",")) {
            set.add(tableName.toUpperCase());
        }
        oracleToMysql(set);
        return "success";
    }

    private void oracleToMysql(Set<String> tableNameSet) throws InstantiationException, IllegalAccessException, ClassNotFoundException {

        // 表信息转化实体类
        List<TableEntity> tableEntityList = new ArrayList<>();
        List<ColumnEntity> columnEntityList = null;
        TableEntity tableEntity = null;
        ColumnEntity columnEntity = null;
        // 主键
        String columnPk = null;
        // 表注释
        OracleTableComments oracleTableComments = null;
        // 所有列注释
        List<OracleColumnComments> columnCommentsList = null;
        // 列注释key 列名 value 注释
        Map<String, String> columnCommentsMap = null;
        // 所有列信息
        List<OracelColumnAllMessageEntity> oracelColumnAllMessageList = null;
        // 类型直接转化枚举类
        OracleToMysqlDataTypeEnum oracleToMysqlDataTypeEnum;
        // JAVA类型转MYSQL类型
        JavaToMysqlDataTypeEnum javaToMysqlDataTypeEnum;
        // 名称类型 名称全大写
        Map<String, String> nameTypeMap = null;
        /**
         * 表字类型转化中实体类中不存在的字段 表名
         */
        List<String> exitTableColumn = new ArrayList<>();
        if (tableNameSet == null) {
            tableNameSet = initOver.getTableNameMap().keySet();
        }
        for (String tableName : tableNameSet) {
            // 1 表初化
            tableEntity = new TableEntity();
            tableEntity.setTableName(tableName);
            // 主键
            columnPk = codeGenService.principalColumnName(tableName);
            // 表注释
            oracleTableComments = codeGenService.tableComments(tableName);
            if (oracleTableComments != null)
                tableEntity.setCommonts(oracleTableComments.getComments());
            // 列信息
            oracelColumnAllMessageList = codeGenService.queryColumnsAllMessage(tableName);
            // 列注释
            columnCommentsList = codeGenService.columnComments(tableName);
            columnCommentsMap = new HashMap<>();
            if (!columnCommentsList.isEmpty()) {
                for (OracleColumnComments oracleColumnComments : columnCommentsList) {
                    columnCommentsMap.put(oracleColumnComments.getColumnName(), oracleColumnComments.getComments());
                }
            }
            // 初始化实体MYSQL信息
            columnEntityList = new ArrayList<>();
            // 获得所有实体类属性
            nameTypeMap = ObjectUtil.getFiledsInfoMap(Class.forName(initOver.getTableNameMap().get(tableName)).newInstance());
            for (OracelColumnAllMessageEntity entity : oracelColumnAllMessageList) {
                columnEntity = BeanMapper.map(entity, ColumnEntity.class);
                if (columnCommentsMap.containsKey(columnEntity.getColumnName())) {
                    columnEntity.setComments(columnCommentsMap.get(columnEntity.getColumnName()));
                }
                if (OracleToMysqlDataTypeEnum.TYPE_VARCHAR2.getOracleDataType().equals(columnEntity.getDataType()) && Integer.parseInt(columnEntity.getDataLength()) > 255) {
                    columnEntity.setDataType(OracleToMysqlDataTypeEnum.TYPE_CLOB.getOracleDataType());
                    columnEntity.setDataLength(null);
                }
                // 转化为MYSQL 数据类型
                // 1通过数据类型可以确定转换的
                oracleToMysqlDataTypeEnum = OracleToMysqlDataTypeEnum.code(columnEntity.getDataType());
                if (oracleToMysqlDataTypeEnum != null) {
                    if (!oracleToMysqlDataTypeEnum.isLimitLengthFlag()) {
                        columnEntity.setDataPrecision(null);
                        columnEntity.setDataScale(null);
                        columnEntity.setDataLength(null);
                    }
                    columnEntity.setDataType(oracleToMysqlDataTypeEnum.getMysqlDataType());
                } else {
                    // 2通过实体类确定数据类型的
                    javaToMysqlDataTypeEnum = JavaToMysqlDataTypeEnum.code(nameTypeMap.get(columnEntity.getColumnName().replace("_", "").toUpperCase()));
                    if (javaToMysqlDataTypeEnum == null) {
                        System.err.println(tableName + ":column" + columnEntity.getColumnName() + ":DataType" + columnEntity.getDataType());
                    } else {
                        if (!javaToMysqlDataTypeEnum.isLimitLengthFlag()) {
                            columnEntity.setDataPrecision(null);
                            columnEntity.setDataScale(null);
                            columnEntity.setDataLength(null);
                        }
                        columnEntity.setDataType(javaToMysqlDataTypeEnum.getMysqlDataType());
                    }
                }

                // 判断是否主键
                if (StringUtils.isNoneBlank(columnPk) && null == tableEntity.getPk() && columnEntity.getColumnName().trim().equals(columnPk.trim())) {
                    tableEntity.setPk(columnEntity);
                } else {
                    columnEntityList.add(columnEntity);
                }
            }
            tableEntity.setColumnEntityList(columnEntityList);
            tableEntityList.add(tableEntity);

        }
        // 通过模版生成MYSQL语句
        GroupTemplate groupTemplate = beetlGroupUtilConfiguration.getGroupTemplate();
        Template template = groupTemplate.getTemplate("mysqlTemplate.html");
        FileWriter writer;
        try {

            for (TableEntity table : tableEntityList) {
                template = groupTemplate.getTemplate("mysqlTemplate.html");
                template.binding("tableEntity", table);
                writer = new FileWriter("D:/mysqlTable/" + table.getTableName() + ".sql");
                template.renderTo(writer);
                writer.close();
            }

            template = groupTemplate.getTemplate("mysqlTemplateInit.html");
            template.binding("tableEntityList", tableEntityList);
            writer = new FileWriter("D:/mysqlTable/init.sql");
            template.renderTo(writer);
            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
