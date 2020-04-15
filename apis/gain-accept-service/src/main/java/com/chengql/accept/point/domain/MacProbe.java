package com.hwsafe.accept.point.domain;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 监测探头信息表
 * 
 * @author chengql
 * @email chengql@hanwei.cn
 * @date 2019-12-24 09:41:01
 */
@Data
@TableName(value = "MAC_PROBE")
public class MacProbe implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * GPRS模块主键ID
     */
    private String gprsid;
    /**
     * GRPS模块ID
     */
    private String gprsmoduleid;
    /**
     * 探头在GPRS中的地址
     */
    private String probeaddress;
    /**
     * 上传地址端口
     */
    private Double endtoport;
    /**
     * 上传IP
     */
    private String ip;
    /**
     * 上位机端口
     */
    private Double port;
    /**
     * dcsid
     */
    private String dcsid;
    /**
     * 设备基础信息id
     */
    private String deviceinfoid;
    /**
     * 二次仪表id
     */
    private String controllerid;
    /**
     * 接入类型 1常规 0dcs
     */
    private String accesstype;
    /**
     * 主键ID
     */
    @TableId
    private String probeid;
    /**
     * 主机id
     */
    private String probehostid;
    /**
     * 编号
     */
    private String probenum;
    /**
     * 品牌
     */
    private String brandid;
    /**
     * 型号
     */
    private String brandtypeid;
    /**
     * 摄像头名称
     */
    private String probename;
    /**
     * 监测类型
     */
    private String probetype;
    /**
     * $column.comments
     */
    private Float range;
    /**
     * 分组
     */
    private String probegroup;
    /**
     * 状态 1.在线 2离线 3维修
     */
    private String state;
    /**
     * 删除标志 1删除 0未删除
     */
    private String isdel;
    /**
     * 备注
     */
    private String notes;
    /**
     * 更新时间
     */
    private Date updatetime;
    /**
     * $column.comments
     */
    private String unit;
    /**
     * 探头距离页面顶部位置
     */
    private Double top;
    /**
     * 探头距离页面左侧位置
     */
    private Double left;
    /**
     * 类型：1为探头，2为摄像头
     */
    private String mactype;
    /**
     * 采集设备名称
     */
    private String gatherequpname;
    /**
     * 量程上限
     */
    private Float rangemax;
    /**
     * 量程下限
     */
    private Float rangelow;
    /**
     * 低报阈值
     */
    private Float lowalarmvalue;
    /**
     * 高报阈值
     */
    private Float highalarmvalue;
    /**
     * 超低报阈值
     */
    private Float superlowalarmvalue;
    /**
     * 超高报阈值
     */
    private Float superhighalarmvalue;
    /**
     * 是否可见0可见 1不可见
     */
    private String visible;

}
