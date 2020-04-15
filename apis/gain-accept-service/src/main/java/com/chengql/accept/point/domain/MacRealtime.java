package com.hwsafe.accept.point.domain;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 实时监测表
 * 
 * @author chengql
 * @email chengql@hanwei.cn
 * @date 2019-12-23 17:30:45
 */
@Data
@TableName(value = "MAC_REALTIME")
public class MacRealtime implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId
    private String realtimeid;
    /**
     * 探头id
     */
    private String probeid;
    /**
     * 浓度值
     */
    private Double chroval;
    /**
     * 监测状态
     */
    private String state;
    /**
     * 创建时间
     */
    private Date createtime;
    /**
     * 更新时间
     */
    private Date updatetime;
    /**
     * 消音标志位 1以消音 0.未消音
     */
    private String iserasure;
    /**
     * 故障开始时间
     */
    private Date starttime;
    /**
     * 故障结束时间
     */
    private Date endtime;

}
