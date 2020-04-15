package com.hwsafe.demo.domain;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * @author chengql
 * 
 *         PO：持久对象 (persistent object)，po(persistent object)就是在Object /Relation
 *         Mapping框架中的Entity，po的每个属性基本上都对应数据库表里面的某个字段。 完全是一个符合Java
 *         Bean规范的纯Java对象，没有增加别的属性和方法。持久对象是由insert
 *         数据库创建，由数据库delete删除的。基本上持久对象生命周期和数据库密切相关。
 *
 */
@Data
public class Person implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String id;
    @NotBlank
    private String name;
    @NotNull
    private Integer age;

}
