package com.chengql.accept.point.domain;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 探头监测历史5分钟数据
 * 
 * @author chengql
 * @email chengql@hanwei.cn
 * @date 2019-12-23 17:05:45
 */
@Data
@TableName(value = "MAC_HISTORY_5M")
public class MacHistory5m implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId
    private String datarecordid;
    /**
     * 探头ID
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
     * 创建日期
     */
    private Date createtime;
    /**
     * 企业名称
     */
    private String entname;
    /**
     * 探头名称
     */
    private String probename;
    /**
     * 探头编号
     */
    private String probenum;
    /**
     * 单位
     */
    private String unit;
    /**
     * 企业id
     */
    private String businessinfoid;

}
