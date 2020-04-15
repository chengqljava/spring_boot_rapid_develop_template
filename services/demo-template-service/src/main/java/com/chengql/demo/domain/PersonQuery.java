package com.hwsafe.demo.domain;

import java.io.Serializable;

import lombok.Data;

/**
 * @author chengql 查询参数
 *
 */
@Data
public class PersonQuery implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private int current;
    private int size;
    private String name;

}
