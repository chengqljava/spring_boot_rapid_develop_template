package com.hwsafe.accept.base.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * @author chengql 转发配置
 *
 */
@Configuration
@Data
public class TranspondConfig {
    /**
     * IP 地址
     */
    @Value("${transpond.ip}")
    public String ip;
    /**
     * 端口
     */
    @Value("${transpond.port}")
    public String port;

    // #转发实时数据方法
    @Value("${transpond.realTime.method}")
    public String realTimeMethod;
    // #转发故障报警处理数据方法
    @Value("${transpond.alarmHandle.method}")
    public String alarmHandleMethod;
    // #转发故障报警数据方法
    @Value("${transpond.alarmFault.method}")
    public String alarmFaultMethod;
    // #转发点位实时数据方法
    @Value("${transpond.pointHistory.method}")
    public String pointHistoryMethod;
    // #转发点位实时数据5m方法
    @Value("${transpond.pointHistory5m.method}")
    public String pointHistory5mMethod;
    // #转发点位实时数据1h方法
    @Value("${transpond.pointHistory1h.method}")
    public String pointHistory1hMethod;
}
