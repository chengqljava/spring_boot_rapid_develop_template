package com.hwsafe.position.domain;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 镇数据库
 * 
 * @author chengql
 * @email chengql@hanwei.cn
 * @date 2019-09-10 11:31:05
 */
@Data
@TableName(value = "position_town")
public class PositionTown implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @TableId
    private Integer id;
    /**
     * 县级市id
     */
    private Long countyId;
    /**
     * 镇id
     */
    private Long townId;
    /**
     * 
     */
    private String townName;

}
