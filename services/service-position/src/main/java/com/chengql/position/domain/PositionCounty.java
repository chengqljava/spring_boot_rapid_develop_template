package com.hwsafe.position.domain;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 地区市数据库
 * 
 * @author chengql
 * @email chengql@hanwei.cn
 * @date 2019-09-10 11:31:05
 */
@Data
@TableName(value = "position_county")
public class PositionCounty implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 地级市主键ID
     */
    @TableId
    private Integer id;
    /**
     * 地级市id
     */
    private Long cityId;
    /**
     * 县级id
     */
    private Long countyId;
    /**
     * 
     */
    private String countyName;

}
