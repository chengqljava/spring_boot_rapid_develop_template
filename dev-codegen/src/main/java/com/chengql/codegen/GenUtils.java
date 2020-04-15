package com.hwsafe.codegen;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springside.modules.utils.base.PropertiesUtil;
import org.springside.modules.utils.io.FileUtil;
import org.springside.modules.utils.io.IOUtil;
import org.springside.modules.utils.time.DateFormatUtil;

import com.hwsafe.codegen.domain.ColumnEntity;
import com.hwsafe.codegen.domain.TableEntity;

public class GenUtils {

    public static List<String> getTemplates() {
        List<String> templates = new ArrayList<String>();
        templates.add("template/Entity.java.vm");
        // templates.add("template/DO.java.vm");
        templates.add("template/DTO.java.vm");
        templates.add("template/Query.java.vm");
        templates.add("template/Mapper.java.vm");
        templates.add("template/Mapper.xml.vm");
        templates.add("template/Service.java.vm");
        templates.add("template/Controller.java.vm");
        // templates.add("template/vue/list.vue.vm");
        // templates.add("template/vue/add.vue.vm");
        // templates.add("template/vue/edit.vue.vm");
        return templates;
    }

    public static void generatorCode(Map<String, String> table,
            List<Map<String, String>> columns) {

        Properties config = getConfig();

        // 表信息
        TableEntity tableEntity = new TableEntity();
        tableEntity.setTableName(table.get("tableName"));
        tableEntity.setComments(table.get("tableComment"));
        // 表名转换成Java类名
        String className = tableToJava(tableEntity.getTableName(),
                PropertiesUtil.getString(config, "tablePrefix", ""));
        tableEntity.setClassName(className);
        tableEntity.setClassname(StringUtils.uncapitalize(className));

        // 列信息
        List<ColumnEntity> columsList = new ArrayList<ColumnEntity>();
        for (Map<String, String> column : columns) {
            ColumnEntity columnEntity = new ColumnEntity();
            columnEntity.setColumnName(column.get("columnName"));
            columnEntity.setDataType(column.get("dataType"));
            columnEntity.setComments(column.get("columnComment"));
            columnEntity.setExtra(column.get("extra"));

            // 列名转换成Java属性名
            String attrName = columnToJava(columnEntity.getColumnName());
            columnEntity.setAttrNameUpper(attrName);
            columnEntity.setAttrNameLower(StringUtils.uncapitalize(attrName));

            // 列的数据类型，转换成Java类型
            String attrType = PropertiesUtil.getString(config,
                    columnEntity.getDataType(), "unknowType");
            columnEntity.setAttrType(attrType);

            // 是否主键
            if ("PRI".equalsIgnoreCase(column.get("columnKey"))
                    && tableEntity.getPk() == null) {
                tableEntity.setPk(columnEntity);
            }

            columsList.add(columnEntity);
        }
        tableEntity.setColumns(columsList);

        // 没主键，则第一个字段为主键
        if (tableEntity.getPk() == null) {
            tableEntity.setPk(tableEntity.getColumns().get(0));
        }

        // 设置velocity资源加载器
        Properties prop = new Properties();
        prop.put("file.resource.loader.class",
                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(prop);

        // 封装模板数据
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("tableName", tableEntity.getTableName());
        map.put("comments", tableEntity.getComments());
        map.put("pk", tableEntity.getPk());
        map.put("className", tableEntity.getClassName());
        map.put("classname",
                Character.toLowerCase(tableEntity.getClassName().charAt(0))
                        + tableEntity.getClassName().substring(1));
        map.put("pathName", tableEntity.getClassname().toLowerCase());
        map.put("columns", tableEntity.getColumns());
        map.put("package",
                PropertiesUtil.getString(config, "package", "com.wlgroup"));
        map.put("author", PropertiesUtil.getString(config, "author", "eric"));
        map.put("email", PropertiesUtil.getString(config, "email",
                "zhangmingming@wulingd.com"));
        map.put("datetime",
                DateFormatUtil.DEFAULT_ON_SECOND_FORMAT.format(new Date()));
        VelocityContext context = new VelocityContext(map);

        // 获取模板列表
        List<String> templates = getTemplates();
        for (String template : templates) {
            // 渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, "UTF-8");
            tpl.merge(context, sw);

            try {
                String fname = getFileName(template, tableEntity.getClassName(),
                        PropertiesUtil.getString(config, "package",
                                "com.hwsafe"));
                FileUtil.makesureParentDirExists(new File(fname));
                IOUtil.write(sw.toString(), FileUtil.asOututStream(fname));
            } catch (IOException e) {
                throw new RuntimeException(
                        "渲染模板失败，表名：" + tableEntity.getTableName(), e);
            }
        }
    }

    /**
     * 列名转换成Java属性名
     */
    public static String columnToJava(String columnName) {
        return WordUtils.capitalizeFully(columnName, new char[] { '_' })
                .replace("_", "");
    }

    /**
     * 表名转换成Java类名
     */
    public static String tableToJava(String tableName, String tablePrefix) {
        if (org.apache.commons.lang.StringUtils.isNotBlank(tablePrefix)) {
            tableName = tableName.replace(tablePrefix, "");
        }
        return columnToJava(tableName);
    }

    /**
     * 获取配置信息
     */
    public static Properties getConfig() {
        return PropertiesUtil.loadFromFile("classpath://generator.properties");
    }

    public static String getFileName(String template, String className,
            String packageName) throws IOException {
        Properties config = getConfig();
        String packagePath = System.getProperty("user.dir") + "/gened/";
        if (StringUtils.isNotBlank(config.getProperty("pathRoot"))) {
            packagePath = config.getProperty("pathRoot");
        } else {
            packagePath = System.getProperty("user.dir") + "/gened/";
        }
        if (StringUtils.isNotBlank(packageName)) {
            packagePath += packageName.replace(".", File.separator)
                    + File.separator;
        }

        if (template.contains("DO.java.vm")) {
            return packagePath + "domain" + File.separator + className
                    + "DO.java";
        }
        if (template.contains("Entity.java.vm")) {
            return packagePath + "domain" + File.separator + className
                    + ".java";
        }

        if (template.contains("DTO.java.vm")) {
            return packagePath + "controller" + "/dto" + File.separator
                    + className + "DTO.java";
        }

        if (template.contains("Query.java.vm")) {
            return packagePath + "domain" + "/query" + File.separator
                    + className + "Query.java";
        }

        if (template.contains("Mapper.java.vm")) {
            return packagePath + "mapper" + File.separator + className
                    + "Mapper.java";
        }

        if (template.contains("Mapper.xml.vm")) {
            return packagePath + "mapper" + File.separator + className
                    + "Mapper.xml";
        }

        if (template.contains("Service.java.vm")) {
            return packagePath + "service" + File.separator + className
                    + "Service.java";
        }

        if (template.contains("Controller.java.vm")) {
            return packagePath + "controller" + File.separator + className
                    + "Controller.java";
        }
        if (template.contains("add.vue.vm")) {
            return packagePath + "vue" + File.separator
                    + className.toLowerCase() + "-add.vue";
        }

        if (template.contains("list.vue.vm")) {
            return packagePath + "vue" + File.separator
                    + className.toLowerCase() + "-list.vue";
        }

        if (template.contains("edit.vue.vm")) {
            return packagePath + "vue" + File.separator
                    + className.toLowerCase() + "-edit.vue";
        }

        if (template.contains("detail.vue.vm")) {
            return packagePath + "vue" + File.separator
                    + className.toLowerCase() + "-detail.java";
        }

        return null;
    }

    /**
     * @param table
     * @param columns
     *            oracle表转为mysql表
     */
    public static void generatorOracleToMysql(Map<String, String> table,
            List<Map<String, String>> columns) {

        Properties config = getConfig();

        // 表信息
        TableEntity tableEntity = new TableEntity();
        tableEntity.setTableName(table.get("tableName"));
        tableEntity.setComments(table.get("tableComment"));
        // 表名转换成Java类名
        String className = tableToJava(tableEntity.getTableName(),
                PropertiesUtil.getString(config, "tablePrefix", ""));
        tableEntity.setClassName(className);
        tableEntity.setClassname(StringUtils.uncapitalize(className));

        // 列信息
        List<ColumnEntity> columsList = new ArrayList<ColumnEntity>();
        for (Map<String, String> column : columns) {
            ColumnEntity columnEntity = new ColumnEntity();
            columnEntity.setColumnName(column.get("columnName"));
            columnEntity.setDataType(column.get("dataType"));
            columnEntity.setComments(column.get("columnComment"));
            columnEntity.setExtra(column.get("extra"));

            columsList.add(columnEntity);
        }
        tableEntity.setColumns(columsList);

        // 没主键，则第一个字段为主键
        if (tableEntity.getPk() == null) {
            tableEntity.setPk(tableEntity.getColumns().get(0));
        }

        // 设置velocity资源加载器
        Properties prop = new Properties();
        prop.put("file.resource.loader.class",
                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(prop);

        // 封装模板数据
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("tableName", tableEntity.getTableName());
        map.put("comments", tableEntity.getComments());
        map.put("pk", tableEntity.getPk());
        map.put("className", tableEntity.getClassName());
        map.put("classname",
                Character.toLowerCase(tableEntity.getClassName().charAt(0))
                        + tableEntity.getClassName().substring(1));
        map.put("pathName", tableEntity.getClassname().toLowerCase());
        map.put("columns", tableEntity.getColumns());
        map.put("package",
                PropertiesUtil.getString(config, "package", "com.wlgroup"));
        map.put("author", PropertiesUtil.getString(config, "author", "eric"));
        map.put("email", PropertiesUtil.getString(config, "email",
                "zhangmingming@wulingd.com"));
        map.put("datetime",
                DateFormatUtil.DEFAULT_ON_SECOND_FORMAT.format(new Date()));
        VelocityContext context = new VelocityContext(map);

        // 获取模板列表
        List<String> templates = getTemplates();
        for (String template : templates) {
            // 渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, "UTF-8");
            tpl.merge(context, sw);

            try {
                String fname = getFileName(template, tableEntity.getClassName(),
                        PropertiesUtil.getString(config, "package",
                                "com.hwsafe"));
                FileUtil.makesureParentDirExists(new File(fname));
                IOUtil.write(sw.toString(), FileUtil.asOututStream(fname));
            } catch (IOException e) {
                throw new RuntimeException(
                        "渲染模板失败，表名：" + tableEntity.getTableName(), e);
            }
        }
    }

    public static Map<String, String> dataTypeOracleToMysql() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("VARCHAR2", "VARCHAR");
        map.put("NUMBER", "BIGINT");
        map.put("TINYINT", "TINYINT");
        map.put("SMALLINT", "SMALLINT");
        map.put("INT", "INT");
        map.put("FLOAT", "FLOAT");
        map.put("DOUBLE", "DOUBLE");
        map.put("DATE", "DATETIME");
        map.put("CLOB", "TEXT");
        map.put("BLOB", "BLOB");
        map.put("INTEGER", "INTEGER");
        map.put("CHAR", "CHAR");
        return map;
    }
}
