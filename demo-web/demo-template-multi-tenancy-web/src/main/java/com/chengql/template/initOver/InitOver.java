/**
 * 文件名：InitOver.java
 *
 * 版本信息：
 * 日期：2020年4月1日
 * Copyright 河南汉威电子股份有限公司软件部 Corporation 2020 
 * 版权所有
 *
 */
package com.hwsafe.template.initOver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hwsafe.common.SpringContextHolder;
import com.hwsafe.template.mapper.CodeGenDao;

/**
 * 服务启动完以后加载
 */
@Component
public class InitOver implements CommandLineRunner {
    // 表名 实体类路径
    private Map<String, String> tableNameMap = null;
    // 表名列表
    private List<String> tableNameList = null;
    @Autowired
    private CodeGenDao codeGenDao;

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.boot.CommandLineRunner#run(java.lang.String[])
     */
    @Override
    public void run(String... args) throws Exception {
        // 查找所有继承类
        Map<String, ServiceImpl> map = SpringContextHolder.getBeansOfType(ServiceImpl.class);
        // 所有实体类加载路径
        List<String> domainList = new ArrayList<>();
        // 错误实体类
        List<String> errorDomainList = new ArrayList<>();
        // 表名 实体类路径
        tableNameMap = new HashMap<>();
        // 表名列表
        tableNameList = new ArrayList<>();
        // 实体类未声明表名为空List
        List<String> tableNameNullList = new ArrayList<>();
        // 实体类声明表名 数据库不存在
        List<String> tableNameExitList = new ArrayList<>();
        String pathEntity = null;
        Class clszz = null;
        for (String key : map.keySet()) {

            pathEntity = SpringContextHolder.getBean(map.get(key).getClass()).toString();
            pathEntity = pathEntity.replace("service", "domain");
            int index = pathEntity.lastIndexOf("Service");
            pathEntity = pathEntity.substring(0, index);
            System.out.println(pathEntity);
            domainList.add(pathEntity);
            try {
                clszz = Class.forName(pathEntity);
                TableName annotation = (TableName) clszz.getAnnotation(TableName.class);
                if (annotation != null) {
                    if (StringUtils.isBlank(annotation.value())) {

                        tableNameNullList.add(pathEntity);

                    } else {
                        if (codeGenDao.queryColumnsAllMessage(annotation.value().toUpperCase()).isEmpty()) {
                            tableNameExitList.add(annotation.value());
                        } else {
                            tableNameMap.put(annotation.value().toUpperCase(), pathEntity);
                            tableNameList.add(annotation.value().toUpperCase());
                        }
                    }
                }

            } catch (Exception e) {
                // e.printStackTrace();
                // com.hwsafe.sys.domain.SysDistrict
                errorDomainList.add(pathEntity);
            }
        }
        if (!tableNameExitList.isEmpty()) {
            System.err.println("实体类声明表名 数据库不存在" + tableNameExitList.size() + " " + JSONObject.toJSONString(tableNameExitList));
        }

        if (!tableNameNullList.isEmpty()) {
            System.err.println("实体类未声明表名" + tableNameNullList.size() + " " + JSONObject.toJSONString(tableNameNullList));
        }

    }

    /**
     * tableNameMap
     *
     * @return the tableNameMap
     * @since CodingExample Ver(编码范例查看) 1.0
     */

    public Map<String, String> getTableNameMap() {
        return tableNameMap;
    }

    /**
     * tableNameList
     *
     * @return the tableNameList
     * @since CodingExample Ver(编码范例查看) 1.0
     */

    public List<String> getTableNameList() {
        return tableNameList;
    }

}
