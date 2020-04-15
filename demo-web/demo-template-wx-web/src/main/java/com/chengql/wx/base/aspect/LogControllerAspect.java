package com.hwsafe.wx.base.aspect;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSONObject;

/**
 * @author chengql 拦截服务执行时间 说明： 1、在类的主体上加上注解定义切面并申明 @Aspect 定义切面
 *         2、@Pointcut("execution(* com.hwsafe.controller..*.*(..))")
 *         定义切入点，一般使用表达式申明切入的范围 如com.hwsafe.service 包下的所有方法都会被拦截切面到
 *         3、@Before:切入点开始执行前处理的方法 4、@After:切入点结尾执行的方法
 *         5、@AfterReturning:在切入点return数据后执行的方法(一般用于对返回数据的包装)
 *         6、@Around:在切入点前后执行的方法 7、@AfterThrowing:抛出异常执行的方法
 */
@Aspect
@Component
public class LogControllerAspect {
    private final static Logger LOGER = LoggerFactory
            .getLogger(LogControllerAspect.class);

    // 定义切点Pointcut
    @Pointcut("execution(* com.hwsafe.*.controller..*.*(..))")
    public void excudeController() {
    }

    @Around("excudeController()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();

        String url = request.getRequestURL().toString();
        String method = request.getMethod();
        String queryString = request.getQueryString();
        Object[] args = pjp.getArgs();
        String params = "";
        // 获取请求参数集合并进行遍历拼接
        if (args.length > 0) {
            if ("POST".equals(method)) {
                Object object = args[0];
                Map<String, Object> map = getKeyAndValue(object);
                params = JSONObject.toJSONString(map);
            } else if ("GET".equals(method)) {
                params = queryString;
            }
        }

        LOGER.info("Controller开始===类型:{}地址:{}参数:{}", method, url, params);
        // result的值就是被拦截方法的返回值
        Object result = pjp.proceed();
        LOGER.info("请求结束===返回值:{}", JSONObject.toJSONString(result));
        return result;
    }

    public static Map<String, Object> getKeyAndValue(Object obj) {
        Map<String, Object> map = new HashMap<>();
        // 得到类对象
        Class<? extends Object> userCla = obj.getClass();
        /* 得到类中的所有属性集合 */
        Field[] fs = userCla.getDeclaredFields();
        for (int i = 0; i < fs.length; i++) {
            Field f = fs[i];
            f.setAccessible(true); // 设置些属性是可以访问的
            Object val = new Object();
            try {
                val = f.get(obj);
                // 得到此属性的值
                map.put(f.getName(), val);// 设置键值
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
        return map;
    }
}
