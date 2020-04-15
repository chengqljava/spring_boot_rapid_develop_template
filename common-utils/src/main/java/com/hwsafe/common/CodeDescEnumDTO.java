package com.hwsafe.common;

import java.io.Serializable;

import lombok.Data;

/**
 * @author chengql 枚举类 DTO
 *
 */
@Data
public class CodeDescEnumDTO<T, E> implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private T code;
    private E desc;

}
