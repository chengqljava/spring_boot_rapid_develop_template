package com.hwsafe.common;

import java.io.Serializable;

import lombok.Data;

/**
 * @author chengql
 * @date 2019/05/21 树节点
 */
@Data
public class TreeNode implements Serializable {
    /**
    *
    */
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private String id;
    /**
     * 名称
     */
    private String name;
    /**
     * 父节点
     */
    private String parentId;

    /**
     * 节点隐患总数
     */
    private String num;

}
