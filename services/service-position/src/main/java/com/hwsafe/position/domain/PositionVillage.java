package com.hwsafe.position.domain;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 省市县镇村数据
 * 
 * @author chengql
 * @email chengql@hanwei.cn
 * @date 2019-09-10 11:31:05
 */
@Data
@TableName(value = "position_village")
public class PositionVillage implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @TableId
    private Integer id;
    /**
     * 镇id
     */
    private Long townId;
    /**
     * 村id--唯一
     */
    private Long villageId;
    /**
     * 
     */
    private String villageName;

}
