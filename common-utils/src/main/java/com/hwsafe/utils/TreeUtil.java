package com.hwsafe.utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.hwsafe.common.TreeNode;

/**
 * @author chengql
 * @date 2019/05/21 树形结构
 */
public class TreeUtil {
    public static Map<String, Object> mapArray = new LinkedHashMap<String, Object>();
    public List<TreeNode> treeNodeCommon;
    public List<Object> list;
    private static TreeUtil treeUtil;

    private TreeUtil() {

    }

    public static TreeUtil getTreeUtil() {
        if (treeUtil == null)
            treeUtil = new TreeUtil();
        return treeUtil;
    }

    /**
     * @param treeNodes
     * @return 生成树
     */
    public List<Object> treeNodeList(List<TreeNode> treeNodes) {
        this.treeNodeCommon = treeNodes;
        list = new ArrayList<Object>();
        boolean parentFlag = false;
        for (TreeNode treeNode : treeNodes) {
            if (StringUtils.isBlank(treeNode.getParentId())) {
                parentFlag = true;
                break;
            }
        }
        for (TreeNode treeNode : treeNodes) {
            Map<String, Object> mapArr = new LinkedHashMap<String, Object>();
            if (parentFlag) {
                if (StringUtils.isBlank(treeNode.getParentId())) {
                    mapArr.put("id", treeNode.getId());
                    mapArr.put("name", treeNode.getName());
                    mapArr.put("parentId", treeNode.getParentId());
                    mapArr.put("sortNo", treeNode.getParentId());
                    mapArr.put("children", treeNodeChild(treeNode.getId()));
                    mapArr.put("num", treeNode.getNum());
                    list.add(mapArr);
                }
            } else {
                mapArr.put("id", treeNode.getId());
                mapArr.put("name", treeNode.getName());
                mapArr.put("parentId", treeNode.getParentId());
                mapArr.put("sortNo", treeNode.getParentId());
                mapArr.put("num", treeNode.getNum());
                list.add(mapArr);
            }
        }
        return list;
    }

    /**
     * @param treeNodes
     * @return 生成树
     */
    public List<Object> treeNodeLists(List<TreeNode> treeNodes) {
        this.treeNodeCommon = treeNodes;

        Map<String, TreeNode> map = new LinkedHashMap<>();
        for (TreeNode node : treeNodes) {
            map.put(node.getId(), node);
        }
        list = new ArrayList<Object>();
        for (TreeNode treeNode : treeNodes) {
            Map<String, Object> mapArr = new LinkedHashMap<String, Object>();
            if (StringUtil.isBlank(treeNode.getParentId())
                    || map.get(treeNode.getParentId()) == null) {
                mapArr.put("name", treeNode.getName());
                mapArr.put("parentId", treeNode.getParentId());
                mapArr.put("sortNo", treeNode.getParentId());
                mapArr.put("children", treeNodeChild(treeNode.getId()));
                mapArr.put("num", treeNode.getNum());
                list.add(mapArr);
            }
        }
        return list;
    }

    /**
     * @param id
     * @return 子节点树加载
     */
    private List<?> treeNodeChild(String id) {
        List<Object> lists = new ArrayList<Object>();
        for (TreeNode treeNode : treeNodeCommon) {
            Map<String, Object> childArray = new LinkedHashMap<String, Object>();
            if (StringUtils.isNotBlank(treeNode.getParentId())
                    && id.equals(treeNode.getParentId())) {
                childArray.put("id", treeNode.getId());
                childArray.put("name", treeNode.getName());
                childArray.put("parentId", treeNode.getParentId());
                childArray.put("sortNo", treeNode.getParentId());
                childArray.put("num", treeNode.getNum());
                childArray.put("children", treeNodeChild(treeNode.getId()));
                lists.add(childArray);
            }
        }
        return lists;
    }
}
