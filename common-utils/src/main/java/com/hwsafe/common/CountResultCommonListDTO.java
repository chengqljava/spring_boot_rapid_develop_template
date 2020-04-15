package com.hwsafe.common;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 
 * @author 单表指定列名单列统计 com.hwsafe.common.SpecifiesColumnStatistical
 */
@Data
public class CountResultCommonListDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 统计总值
     */
    private List<SpecifiesColumnStatistical> value;
    /**
     * 显示名称
     */
    private String name;

    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        CountResultCommonListDTO dto = (CountResultCommonListDTO) o;
        return dto.getName().equals(name);
    }

}
