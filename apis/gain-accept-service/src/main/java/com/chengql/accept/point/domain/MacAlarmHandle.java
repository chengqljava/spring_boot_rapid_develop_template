package com.hwsafe.accept.point.domain;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 监测报警信息处理表
 * 
 * @author chengql
 * @email chengql@hanwei.cn
 * @date 2019-12-23 17:05:45
 */
@Data
@TableName(value = "MAC_ALARM_HANDLE")
public class MacAlarmHandle implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId
    private String alarmhandleid;
    /**
     * 报警ID
     */
    private String macalarmid;
    /**
     * 处理状态
     */
    private String handlestatus;
    /**
     * 处理时间
     */
    private Date handletime;
    /**
     * 备注
     */
    private String notes;
    /**
     * 报警处理措施
     */
    private String handleway;

}
