package com.hwsafe.accept.point.domain;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 故障报警表
 * 
 * @author chengql
 * @email chengql@hanwei.cn
 * @date 2019-12-23 17:05:45
 */
@Data
@TableName(value = "MAC_ALARM_FAULT")
public class MacAlarmFault implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * $column.comments
     */
    private String businessinfoid;
    /**
     * id
     */
    @TableId
    private String alarmfaultid;
    /**
     * 监测探头id
     */
    private String probeid;
    /**
     * 故障报警开始时间
     */
    private Date starttime;
    /**
     * 故障报警结束时间
     */
    private Date endtime;
    /**
     * 状态
     */
    private String status;
    /**
     * 处理情况
     */
    private String handlestatus;
    /**
     * 处理时间
     */
    private Date handletime;
    /**
     * 描述
     */
    private String notes;

}
