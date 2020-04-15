package com.hwsafe.common;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 自动获取表名
 *
 */
public interface AutomaticAcquisitionTableName {
    /**
     * @param basePack
     *            基础包
     * @param ntoContainsFile
     *            不包含那些包文件
     * @param containsFile
     *            指定包下文件
     * @return 获取指定相应包下所有类
     * @throws ClassNotFoundException
     *             获取类
     */
    public List<Class<?>> tableNameClassList(String basePack,
            String ntoContainsFile, String containsFile)
            throws ClassNotFoundException;

    /**
     * @param basePack
     *            基础包
     * @param ntoContainsFile
     *            不包含那些包文件
     * @param containsFile
     *            指定包下文件
     * @return 获取指定相应包下所有类
     * @throws ClassNotFoundException
     *             获取表名
     */
    public List<String> tableNameList(String basePack, String ntoContainsFile,
            String containsFile) throws ClassNotFoundException;

    public default List<Class<?>> tableNameClassList()
            throws ClassNotFoundException {
        return this.tableNameClassList("com.hwsafe", "query,dto,bo", "domain");
    }

    /**
     * 该方法会得到所有的类，将类的绝对路径写入到classPaths中
     * 
     * @param file
     */
    public default void doPath(File file, List<String> classPaths) {
        if (file.isDirectory()) {// 文件夹
            // 文件夹我们就递归
            File[] files = file.listFiles();
            for (File f1 : files) {
                doPath(f1, classPaths);
            }
        } else {// 标准文件
            // 标准文件我们就判断是否是class文件
            if (file.getName().endsWith(".class")) {
                // 如果是class文件我们就放入我们的集合中。
                classPaths.add(file.getPath());
            }
        }
    }

    static List<String> classPaths = new ArrayList<String>();
    static String containsFile = "domain";
    static String ntoContainsFile = "query,dto,bo";
    static String basePack = "com.hwsafe";

    /**
     * @return 获取本服务下所有表名
     * @throws ClassNotFoundException
     */
    /*
     * public static List<String> tableNameList() throws ClassNotFoundException
     * { List<String> tableNameList = new ArrayList<>(); //
     * 先把包名转换为路径,首先得到项目的classpath String classpath =
     * ChatAutomaticAcquisitionTableName.class.getResource("/").getPath(); //
     * 然后把我们的包名basPach转换为路径名 basePack = basePack.replace(".", File.separator);
     * // 然后把classpath和basePack合并 String searchPath = classpath + basePack;
     * doPath(new File(searchPath)); boolean flag; //
     * 这个时候我们已经得到了指定包下所有的类的绝对路径了。我们现在利用这些绝对路径和java的反射机制得到他们的类对象 for (String s :
     * classPaths) { // 把 //
     * D:\work\code\20170401\search-class\target\classes\com\baibin\search\a\A.
     * class // 这样的绝对路径转换为全类名com.baibin.search.a.A s =
     * s.replace(classpath.replace("/",
     * "\\").replaceFirst("\\\\", ""), "").replace("\\", ".") .replace(".class",
     * ""); flag = true; if (s.contains("domain")) {
     * 
     * for (String notContains : ntoContainsFile.split(",")) { if
     * (s.contains(notContains)) { flag = false; } } if (flag) { Class<?> cls =
     * Class.forName(s); // TableName baseRequestMapping =
     * cls.getAnnotation(TableName.class); //
     * tableNameList.add(baseRequestMapping.value()); } }
     * 
     * }
     * 
     * return tableNameList;
     * 
     * }
     * 
     *//**
        * 该方法会得到所有的类，将类的绝对路径写入到classPaths中
        * 
        * @param file
        *//*
           * private static void doPath(File file) { if (file.isDirectory()) {//
           * 文件夹 // 文件夹我们就递归 File[] files = file.listFiles(); for (File f1 :
           * files) { doPath(f1); } } else {// 标准文件 // 标准文件我们就判断是否是class文件 if
           * (file.getName().endsWith(".class")) { // 如果是class文件我们就放入我们的集合中。
           * classPaths.add(file.getPath()); } } }
           */
}
