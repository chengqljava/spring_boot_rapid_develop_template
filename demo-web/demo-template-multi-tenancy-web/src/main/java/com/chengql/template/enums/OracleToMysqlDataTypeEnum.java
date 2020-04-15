/**
 * 文件名：OracleToMysqlDataTypeEnum.java
 *
 * 版本信息：
 * 日期：2020年3月31日
 * Copyright 河南汉威电子股份有限公司软件部 Corporation 2020 
 * 版权所有
 *
 */
package com.hwsafe.template.enums;

/**
 * oracle数据类型转换mysql数据类型
 * 
 */
public enum OracleToMysqlDataTypeEnum {

    TYPE_DATE("DATE", "datetime", false), TYPE_INT("INT", "integer", true), TYPE_INTEGER("INTEGER", "integer", true), TYPE_LONG("LONG", "bigint", true), TYPE_FLOAT("FLOAT", "float",
            true), TYPE_CLOB("CLOB", "text", false), TYPE_BOLD("BOLD", "bold", false), TYPE_VARCHAR2("VARCHAR2", "VARCHAR", true);
    /**
     * oracle 数据类型
     */
    private String oracleDataType;
    /**
     * mysql数据类型
     */
    private String mysqlDataType;
    /**
     * limitLength
     */
    private boolean limitLengthFlag;

    /**
     * 创建一个新的实例 OracleToMysqlDataTypeEnum.
     *
     * @param oracleDataType
     * @param mysqlDataType
     */
    private OracleToMysqlDataTypeEnum(String oracleDataType, String mysqlDataType, boolean limitLengthFlag) {
        this.oracleDataType = oracleDataType;
        this.mysqlDataType = mysqlDataType;
        this.limitLengthFlag = limitLengthFlag;
    }

    public static OracleToMysqlDataTypeEnum code(String oracleDataType) {
        for (OracleToMysqlDataTypeEnum oracleToMysqlDataTypeEnum : OracleToMysqlDataTypeEnum.values()) {
            if (oracleToMysqlDataTypeEnum.getOracleDataType().equals(oracleDataType)) {
                return oracleToMysqlDataTypeEnum;
            }
        }
        return null;
    }

    /**
     * oracleDataType
     *
     * @return the oracleDataType
     * @since CodingExample Ver(编码范例查看) 1.0
     */

    public String getOracleDataType() {
        return oracleDataType;
    }

    /**
     * mysqlDataType
     *
     * @return the mysqlDataType
     * @since CodingExample Ver(编码范例查看) 1.0
     */

    public String getMysqlDataType() {
        return mysqlDataType;
    }

    /**
     * limitLengthFlag
     *
     * @return the limitLengthFlag
     * @since CodingExample Ver(编码范例查看) 1.0
     */

    public boolean isLimitLengthFlag() {
        return limitLengthFlag;
    }

}
