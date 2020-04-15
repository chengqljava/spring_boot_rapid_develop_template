package com.hwsafe.position.domain;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 省份数据库
 * 
 * @author chengql
 * @email chengql@hanwei.cn
 * @date 2019-09-10 11:31:05
 */
@Data
@TableName(value = "position_provice")
public class PositionProvice implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId
    private Integer id;
    /**
     * 省份id、省份编号
     */
    private Integer proviceId;
    /**
     * 省份名称
     */
    private String proviceName;

}
