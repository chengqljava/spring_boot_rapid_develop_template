package com.hwsafe.template.controller.cotroller_demo.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * @author King DTO：数据传输对象(Data Transfer Object)，是一种设计模式之间传输数据的软件应用系统。
 *         数据传输目标往往是数据访问对象从数据库中检索数据。数据传输对象与数据交互对象或数据访问对象之间
 *         的差异是一个以不具有任何行为除了存储和检索的数据（访问和存取器）。简单来说，当我们需要一个对象10个
 *         字段的内容，但这个对象总共有20个字段，我们不需要把整个PO对象全部字段传输到客户端，而是可以用DTO重新封装，
 *         传递到客户端。此时，如果这个对象用来对应界面的展现，就叫VO。
 */
@Data
public class PersonDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private Integer age;
}
