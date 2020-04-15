package com.hwsafe.demo.domain;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("USER")
public class User {
    private Long id;
    /**
     * 租户 ID
     */
    private String tenantId;
    private String name;
}
