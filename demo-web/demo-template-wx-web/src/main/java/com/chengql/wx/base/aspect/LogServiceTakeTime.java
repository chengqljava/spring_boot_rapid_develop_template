package com.hwsafe.wx.base.aspect;

import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;

/**
 * @author chengql 拦截服务执行时间 说明： 1、在类的主体上加上注解定义切面并申明 @Aspect 定义切面
 *         2、@Pointcut("execution(* com.hwsafe.*.service..*.*(..))")
 *         定义切入点，一般使用表达式申明切入的范围 如com.hwsafe.service 包下的所有方法都会被拦截切面到
 *         3、@Before:切入点开始执行前处理的方法 4、@After:切入点结尾执行的方法
 *         5、@AfterReturning:在切入点return数据后执行的方法(一般用于对返回数据的包装)
 *         6、@Around:在切入点前后执行的方法 7、@AfterThrowing:抛出异常执行的方法
 * 
 *         第一个* 代表任意返回类型 第二个* 任一包 第三个.*(类名需要用一个.占位) 任意字符结尾的类名 第四个 *(..) 代表任意方法
 */
@Aspect
@Component
public class LogServiceTakeTime {
    private final static Logger LOGER = LoggerFactory
            .getLogger(LogServiceTakeTime.class);

    @Pointcut("execution(* com.hwsafe.*.service..*.*(..))")
    public void performance() {
    }

    @Around("performance()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {

        // 记录起始时间
        long begin = System.currentTimeMillis();
        Object result = "";
        /** 执行目标方法 */
        try {
            result = joinPoint.proceed();
        } catch (Exception e) {
            LOGER.error("日志记录发生错误, errorMessage: {}", e.getMessage());
        } finally {
            /** 记录操作时间 */
            long took = System.currentTimeMillis() - begin;
            if (took >= 10000) {
                LOGER.error("Service 执行时间为: {}秒", took);
                LOGER.error("Controller 执行时间为: {}毫秒", took);
            } else if (took >= 5000) {
                LOGER.warn("Service 执行时间为: {}秒", took);
                LOGER.warn("Controlle r执行时间为: {}毫秒", took);
            } else if (took >= 3000) {
                LOGER.info("Service执行时间为: {}秒", took);
                LOGER.info("Controller 执行时间为: {}毫秒", took);
            }
            // TODO 日志保存到 数据
        }
        return result;
    }

    @Before("performance()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        if (joinPoint == null) {
            return;
        }
        /**
         * Signature 包含了方法名、申明类型以及地址等信息
         */
        String class_name = joinPoint.getTarget().getClass().getName();
        String method_name = joinPoint.getSignature().getName();
        // 重新定义日志
        LOGER.info("class_name = {}", class_name);
        LOGER.info("method_name = {}", method_name);
        /**
         * 获取方法的参数值数组。
         */
        Object[] method_args = joinPoint.getArgs();

        try {
            /**
             * 获取方法参数名称
             */
            String[] paramNames = getFieldsName(class_name, method_name);

            /**
             * 打印方法的参数名和参数值
             */
            logParam(paramNames, method_args);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 使用javassist来获取方法参数名称
     * 
     * @param class_name
     *            类名
     * @param method_name
     *            方法名
     * @return
     * @throws Exception
     */
    private String[] getFieldsName(String class_name, String method_name)
            throws Exception {
        Class<?> clazz = Class.forName(class_name);
        String clazz_name = clazz.getName();
        ClassPool pool = ClassPool.getDefault();
        ClassClassPath classPath = new ClassClassPath(clazz);
        pool.insertClassPath(classPath);

        CtClass ctClass = pool.get(clazz_name);
        CtMethod ctMethod = ctClass.getDeclaredMethod(method_name);
        MethodInfo methodInfo = ctMethod.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute
                .getAttribute(LocalVariableAttribute.tag);
        if (attr == null) {
            return null;
        }
        String[] paramsArgsName = new String[ctMethod
                .getParameterTypes().length];
        int pos = Modifier.isStatic(ctMethod.getModifiers()) ? 0 : 1;
        for (int i = 0; i < paramsArgsName.length; i++) {
            paramsArgsName[i] = attr.variableName(i + pos);
        }
        return paramsArgsName;
    }

    /**
     * 判断是否为基本类型：包括String
     * 
     * @param clazz
     *            clazz
     * @return true：是; false：不是
     */
    private boolean isPrimite(Class<?> clazz) {
        if (clazz.isPrimitive() || clazz == String.class) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 打印方法参数值 基本类型直接打印，非基本类型需要重写toString方法
     * 
     * @param paramsArgsName
     *            方法参数名数组
     * @param paramsArgsValue
     *            方法参数值数组
     */
    private void logParam(String[] paramsArgsName, Object[] paramsArgsValue) {
        if (ArrayUtils.isEmpty(paramsArgsName)
                || ArrayUtils.isEmpty(paramsArgsValue)) {
            LOGER.info("该方法没有参数");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < paramsArgsName.length; i++) {
            // 参数名
            String name = paramsArgsName[i];
            // 参数值
            Object value = paramsArgsValue[i];
            buffer.append(name + " = ");
            if (isPrimite(value.getClass())) {
                buffer.append(value + "  ,");
            } else {
                buffer.append(value.toString() + "  ,");
            }
        }
        LOGER.info("params{}", buffer.toString());
    }

}
