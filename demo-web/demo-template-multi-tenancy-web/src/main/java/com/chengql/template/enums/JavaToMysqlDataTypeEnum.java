
package com.hwsafe.template.enums;

/**
 * java基本类型转化MYSQL类型
 *
 */
public enum JavaToMysqlDataTypeEnum {

    // java 8大基本类型四种整数类型 byte、short、int、long 两种浮点数类型float、double 一种字符类型(char)
    // 一种布尔类型(boolean)
    TYPE_String("String", "VARCHAR", true), TYPE_byte("byte", "INTEGER", true), TYPE_byte_OBJECT("Byte", "INTEGER", true), TYPE_short("short", "INTEGER", true), TYPE_Short_OBJECT("Short", "INTEGER",
            true), TYPE_int("int", "INTEGER", true), TYPE_int_OBJECT("Integer", "INTEGER", true), TYPE_CHAR("char", "char", true), TYPE_CHAR_OBJECT("Character", "char",
                    true), TYPE_LONG("long", "INTEGER", true), TYPE_LONG_Object("Long", "INTEGER", true), TYPE_BigInteger("BigInteger", "BIGINT", true), TYPE_Float("float", "FLOAT",
                            true), TYPE_Float_Object("Float", "FLOAT", true), TYPE_DOUBLE("double", "DOUBLE", true), TYPE_DOUBLE_OBject("Double", "DOUBLE",
                                    true), TYPE_BOOLEAN_OBJECT("Boolean", "tinyint(1)", false), TYPE_BOOLEAN("boolean", "tinyint(1)", false), TYPE_BigDecimal("BigDecimal", "decimal", true);
    /**
     * oracle 数据类型
     */
    private String javaDataType;
    /**
     * mysql数据类型
     */
    private String mysqlDataType;
    /**
     * limitLength
     */
    private boolean limitLengthFlag;

    /**
     * 创建一个新的实例 JavaToMysqlDataTypeEnum.
     *
     * @param javaDataType
     * @param mysqlDataType
     * @param limitLengthFlag
     */
    private JavaToMysqlDataTypeEnum(String javaDataType, String mysqlDataType, boolean limitLengthFlag) {
        this.javaDataType = javaDataType;
        this.mysqlDataType = mysqlDataType;
        this.limitLengthFlag = limitLengthFlag;
    }

    public static JavaToMysqlDataTypeEnum code(String javaDataType) {
        for (JavaToMysqlDataTypeEnum javaToMysqlDataTypeEnum : JavaToMysqlDataTypeEnum.values()) {
            if (javaToMysqlDataTypeEnum.getJavaDataType().equals(javaDataType)) {
                return javaToMysqlDataTypeEnum;
            }
        }
        return null;
    }

    /**
     * javaDataType
     *
     * @return the javaDataType
     * @since CodingExample Ver(编码范例查看) 1.0
     */

    public String getJavaDataType() {
        return javaDataType;
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
