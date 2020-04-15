package com.hwsafe.qurtz.utils;

import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;
import org.springside.modules.utils.reflect.ReflectionUtil;

import com.hwsafe.common.SpringContextHolder;

public class ScheduleRunnable implements Runnable {

    private Object target;
    private Method method;
    private String params;

    public ScheduleRunnable(String beanName, String methodName, String params)
            throws NoSuchMethodException, SecurityException {
        this.target = SpringContextHolder.getBean(beanName);
        this.params = params;

        if (StringUtils.isNotBlank(params)) {
            this.method = target.getClass().getDeclaredMethod(methodName,
                    String.class);
        } else {
            this.method = target.getClass().getDeclaredMethod(methodName);
        }

    }

    @Override
    public void run() {

        try {
            if (StringUtils.isNotBlank(params)) {
                ReflectionUtil.invokeMethod(target, method, params);
            } else {
                ReflectionUtil.invokeMethod(target, method);
            }
        } catch (Exception e) {
            throw new RuntimeException("执行定时任务失败", e);
        }

    }

}
