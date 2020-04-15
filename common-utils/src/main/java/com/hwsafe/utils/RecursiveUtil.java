package com.hwsafe.utils;

/**  
 * Project Name:platform_utils  
 * File Name:RecursiveUtil.java  
 * Package Name:com.zwsafety.platform.utils  
 * Date:2016年7月4日
 * Copyright (c) 2016,zwsafety All Rights Reserved.   
 */

import java.util.List;

/**
 * @ClassName:RecursiveUtil
 * @Description:TODO(递归工具)
 * @date:2016年7月4日
 * @author yxx
 * @version 1.0
 * @since JDK 1.8
 */
public class RecursiveUtil {

    /**
     * 
     * @Title:recursive
     * @Description TODO(递归算法，返回id的所有子id).
     * @date 2016年7月4日
     * @author yxx
     * @param list
     *            递归bean List
     * @param idList
     *            子id集合（最终返回的结果集） List<String> idList = new ArrayList<String>();
     *            参数直接传递idList即可
     * @param id
     *            指定id
     * @return List<String>
     */
    public static void recursive(List<RecursiveBean> list, List<String> idList,
            String id) {
        for (RecursiveBean bean : list) {
            String tid = bean.getId();
            String pid = bean.getPid();
            if (id.equals(pid)) {
                if (!idList.contains(tid)) {
                    idList.add(tid);
                }
                recursive(list, idList, tid);
            }
        }
    }

    /**
     * 
     * @ClassName:RecursiveBean
     * @Description:TODO(递归bean)
     * @date:2016年7月4日
     * @author yxx
     * @version 1.0
     * @since JDK 1.7
     */
    public static class RecursiveBean {

        /**
         * 
         * Creates a new instance of RecursiveBean.
         */
        public RecursiveBean() {
        }

        /**
         * id
         */
        private String id;

        /**
         * 父id
         */
        private String pid;

        public String getId() {

            return id;
        }

        public void setId(String id) {

            this.id = id;
        }

        public String getPid() {

            return pid;
        }

        public void setPid(String pid) {

            this.pid = pid;
        }
    }
}
