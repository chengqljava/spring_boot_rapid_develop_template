
/**  
* Project Name:module_ems  
* File Name:Distance.java  
* Package Name:com.zwsafety.module.ems.entity  
* Date:2017年10月10日
* Copyright (c) 2017,hwsafety All Rights Reserved.   
*/

package com.hwsafe.utils;

/**
 * @ClassName:Distance
 * @Description:TODO(根据经纬度计算实际距离)
 * @date:2017年10月10日
 * @author Administrator
 * @version 1.0
 * @since: JDK 1.7
 */
public class Distance {

    private static final double EARTH_RADIUS = 6378.137;

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 
     * @Title:GetDistance
     * @Description TODO(根据两个位置的经纬度，来计算两地的距离（单位为KM）). TODO(这里描述这个方法适用条件 – 可选).
     *              TODO(这里描述这个方法的执行流程 – 可选). TODO(这里描述这个方法的使用方法 – 可选).
     *              TODO(这里描述这个方法的注意事项 – 可选).
     * @date 2017年10月10日
     * @author Administrator
     * @param long1
     *            位置1经度
     * @param lat1
     *            位置1纬度
     * @param long2
     *            位置2经度
     * @param lat2
     *            位置2纬度
     * @return
     */
    public static double GetDistance(double long1, double lat1, double long2,
            double lat2) {
        double a, b, d, sa2, sb2;
        lat1 = rad(lat1);
        lat2 = rad(lat2);
        a = lat1 - lat2;
        b = rad(long1 - long2);

        sa2 = Math.sin(a / 2.0);
        sb2 = Math.sin(b / 2.0);
        d = 2 * EARTH_RADIUS * Math.asin(Math
                .sqrt(sa2 * sa2 + Math.cos(lat1) * Math.cos(lat2) * sb2 * sb2));
        return d;
    }

    public static void main(String[] args) {
        System.out.println(GetDistance(110.032, 40.418, 110.033, 40.44));
        System.out.println(GetDistance(20.5, 118.2, 21.1, 117.6));
    }
}
