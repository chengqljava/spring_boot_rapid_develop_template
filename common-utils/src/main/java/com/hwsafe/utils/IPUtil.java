/**  
 * Project Name:Utils  
 * File Name:IpUtils.java  
 * Package Name:javaUtil  
 * Date:2015年7月27日
 * Copyright (c) 2015,zwsafety All Rights Reserved.   
 */

package com.hwsafe.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName:IpUtils
 * @Description:TODO(用一句话描述该文件做什么)
 * @date:2015年7月27日
 * @author luyao
 * @version 1.0
 * @since: JDK 1.7
 */
public final class IPUtil {
    /**
     * Creates a new instance of IpUtil.
     */
    private IPUtil() {
        // TODO Auto-generated constructor stub
    }

    /**
     * 二进制32位为全1的整数值
     */
    public static final long ALL32ONE = 4294967295L;
    /**
     * 2的32次方整数值
     */
    public static final long TOW32POWER = 4294967296L;

    /**
     * @Title:twoPower
     * @Description TODO(计算数字是2的几次方，如果不是2的整数次,返回-1).
     * @date 2015年7月29日
     * @author luyao
     * @param num
     *            long
     * @return int
     */
    public static int twoPower(long num) {

        if (num < 1 || num > TOW32POWER) {
            return -1;
        }
        int i = 0;
        int power = 0;
        for (i = 0; i < 32; i++) {
            if ((num & 1) == 1) {
                break;
            } else {
                power++;
                num = num >> 1;
            }
        }
        if ((num >> 1) == 0) {
            return power;
        } else {
            return -1;
        }
    }

    /**
     * @Title:ipStringToLong
     * @Description TODO(单个IP地址字符串到数字的转换，不做验证).
     * @date 2015年7月29日
     * @author luyao
     * @param source
     *            String
     * @return long
     */
    public static final long ipStringToLong(String source) {
        long temp = 0;
        int pos = 0;
        String cur = "";
        pos = source.indexOf(".", 0);
        while (pos != -1) {
            cur = source.substring(0, pos);
            source = source.substring(pos + 1, source.length());
            temp = (temp << 8) | Long.parseLong(cur);
            pos = source.indexOf(".", 0);
        }
        temp = (temp << 8) | Long.parseLong(source);
        return temp;
    }

    /**
     * @Title:ipLongToString
     * @Description TODO(单个IP地址数字到字符串的转换，不做验证).
     * @date 2015年7月29日
     * @author luyao
     * @param source
     *            long
     * @return String
     */
    public static final String ipLongToString(long source) {
        long mask = 255;
        long result = 0;
        result = source & mask;
        String temp = String.valueOf(result);
        for (int i = 0; i < 3; i++) {
            source = source >> 8;
            result = source & mask;
            temp = String.valueOf(result) + "." + temp;
        }
        return temp.toString();
    }

    /**
     * @Title:isValidSegString
     * @Description TODO(验证IP地址段合法性).
     * @date 2015年7月29日
     * @author luyao
     * @param start
     *            String
     * @param end
     *            String
     * @return boolean
     */
    public static final boolean isValidSegString(String start, String end) {
        long s = ipStringToLong(start);
        long e = ipStringToLong(end);
        return isValidSegLong(s, e);
    }

    /**
     * @Title:isValidSegLong
     * @Description TODO(验证IP地址段合法性 开始、结束地址数值小于ALLONE大于0； 开始地址小于等于结束地址 （开始地址 –
     *              结束地址 + 1）是2的整数次方 ).
     * @date 2015年7月29日
     * @author luyao
     * @param start
     *            开始地址
     * @param end
     *            结束地址
     * @return boolean
     */
    public static final boolean isValidSegLong(long start, long end) {
        if ((start > end) || (start > IPUtil.ALL32ONE)
                || (end > IPUtil.ALL32ONE) || (end < 0) || (start < 0)) {
            return false;
        }
        int n = 0;
        n = IPUtil.twoPower(end - start + 1);
        if (-1 == n) {
            return false;
        }
        return (start >> n) == (end >> n);
    }

    /**
     * @Title:repeatSplit
     * @Description TODO(循环分割).
     * @date 2015年7月29日
     * @author luyao
     * @param sip
     *            long
     * @param eip
     *            long
     * @param low
     *            long
     * @param high
     *            long
     * @param vc
     *            Vector
     */
    private static void repeatSplit(long sip, long eip, long low, long high,
            Vector<long[]> vc) {
        long[] temp = new long[2];
        // 控制最小的分割地址段大小，原来是4，目前改为0，即可以分一个地址
        if ((high - low) == 0 || ((sip == low) && (eip == high))) {
            temp[0] = low;
            temp[1] = high;
            vc.addElement(temp);
            return;
        }
        long mid = (low + high - 1) / 2;

        if (mid < sip) {
            temp[0] = low;
            temp[1] = mid;
            vc.addElement(temp);
            repeatSplit(sip, eip, mid + 1, high, vc);
        } else if (mid >= eip) {
            temp[0] = mid + 1;
            temp[1] = high;
            vc.addElement(temp);
            repeatSplit(sip, eip, low, mid, vc);
        } else {
            vc = null;
            return;
        }
    }

    /**
     * @Title:split
     * @Description TODO(地址拆分 输入合法地址段，但还需要验证包含关系,输出升序).
     * @date 2015年7月29日
     * @author luyao
     * @param sip
     *            子段开始地址
     * @param eip
     *            子段结束地址
     * @param low
     *            基段开始地址
     * @param high
     *            基段结束地址
     * @param sort
     *            是否排序
     * @return Vector<long[]>
     */
    public static Vector<long[]> split(long sip, long eip, long low, long high,
            boolean sort) {
        if (!((low <= sip && sip <= high) && (low <= eip && eip <= high))) {
            return null;
        }
        Vector<long[]> vc = new Vector<long[]>();
        repeatSplit(sip, eip, low, high, vc);
        if (!sort) {
            return vc;
        } else {
            int size = vc.size();
            int j = 0;
            long[] temp = new long[size * 2];
            long[] tempr = new long[2];

            for (j = 0; j < size; j++) {
                tempr = (long[]) vc.elementAt(j);
                temp[2 * j] = tempr[0];
                temp[2 * j + 1] = tempr[1];
            }
            vc = null;
            vc = new Vector<long[]>();
            Arrays.sort(temp);
            for (j = 0; j < size; j++) {
                tempr = new long[2];
                tempr[0] = temp[2 * j];
                tempr[1] = temp[2 * j + 1];
                vc.addElement(tempr);
            }
            return vc;
        }
    }

    /**
     * @Title:unit
     * @Description TODO(地址合并,检查是否连续，检查合并后是否合法).
     * @date 2015年7月29日
     * @author luyao
     * @param vc
     *            合法的地址段VECTOR
     * @return long[]
     */
    public static long[] unit(Vector<?> vc) {
        if (vc == null) {
            return null;
        }
        int size = vc.size();
        int n = size * 2;
        long[] ips = new long[n];
        long[] temp = null;
        int i = 0;
        for (i = 0; i < size; i++) {
            temp = (long[]) vc.elementAt(i);
            if (temp == null) {
                return null;
            }
            ips[2 * i] = temp[0];
            ips[2 * i + 1] = temp[1];
        }
        Arrays.sort(ips);
        n--;
        for (i = 1; i < n; i = i + 2) {
            if (ips[i] + 1 != ips[i + 1]) {
                return null;
            }
        }
        temp[0] = ips[0];
        temp[1] = ips[n];
        ips = null;
        if (IPUtil.isValidSegLong(temp[0], temp[1])) {
            return temp;
        } else {
            return null;
        }
    }

    /**
     * @Title:isValidIp
     * @Description TODO(验证单个IP地址是否合法,保证输入是“.”和数字).
     * @date 2015年7月29日
     * @author luyao
     * @param ip
     *            String
     * @return boolean
     */
    public static boolean isValidIp(String ip) {
        StringTokenizer st = new StringTokenizer(ip, ".");
        int i = 0;
        for (i = 0; st.hasMoreTokens(); i++) {
            int n = Integer.parseInt(st.nextToken());
            if (n > 255 || n < 0) {
                return false;
            }
        }
        return i == 4 ? true : false;
    }

    /**
     * @Title:getSegString
     * @Description TODO(输入LONG型地址段，返回地址段字符串，用 - 连接。要求地址段合法。只用于从数据库中取值转换).
     * @date 2015年7月29日
     * @author luyao
     * @param startip
     *            long
     * @param endip
     *            long
     * @return String
     */
    public static String getSegString(long startip, long endip) {
        return (IPUtil.ipLongToString(startip) + " - "
                + IPUtil.ipLongToString(endip));
    }

    /**
     * @Title:getSegString
     * @Description TODO(输入STRING型地址段，返回地址段字符串，用 - 连接。要求地址段合法。只用于从数据库中取值转换).
     * @date 2015年7月29日
     * @author luyao
     * @param startip
     *            String
     * @param endip
     *            String
     * @return String
     */
    public static String getSegString(String startip, String endip) {
        return (startip + " - " + endip);
    }

    /**
     * @Title:isDigit
     * @Description TODO(检查字符串是否全由数字组成).
     * @date 2015年7月29日
     * @author luyao
     * @param str
     *            String
     * @return boolean
     */
    public static boolean isDigit(String str) {
        int size = str.length();
        for (int i = 0; i < size; i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * @Title:splitScale
     * @Description TODO(验证比例字符串的正确性).
     * @date 2015年7月29日
     * @author luyao
     * @param scale
     *            比例字符串
     * @param sip
     *            被拆分的地址段(long)
     * @param eip
     *            被拆分的地址段(long)
     * @return Vector 正确返回被拆分结果地址段Vector，否则空
     */
    public static Vector<long[]> splitScale(String scale, long sip, long eip) {
        StringTokenizer st = new StringTokenizer(scale, ":");
        int size = st.countTokens();
        int[] scales = new int[size];
        int sum = 0;
        for (int i = 0; i < size; i++) {
            String temp = st.nextToken();
            if (IPUtil.isDigit(temp)) {
                scales[i] = Integer.parseInt(temp);
                sum += scales[i];
            } else {
                return null;
            }
        }
        // 找到最小的数字和它的位置
        int num = scales[0];
        for (int i = 0; i < size; i++) {
            if (num > scales[i]) {
                num = scales[i];
            }
        }
        // 比例和数/比例数 必须是2的整数次并且无余数
        for (int i = 0; i < size; i++) {
            if (sum % scales[i] != 0) {
                return null;
            }
            if (-1 == IPUtil.twoPower(sum / scales[i])) {
                return null;
            }
        }

        long share = 0;
        if ((eip - sip + 1) % sum == 0) {
            share = (eip - sip + 1) / sum;
        } else {
            return null;
        }
        // 分段验证
        Vector<long[]> vc = new Vector<long[]>();
        long s = sip;
        long e = sip - 1;
        for (int i = 0; i < size; i++) {
            s = e + 1;
            e = s + share * scales[i] - 1;
            if (IPUtil.isValidSegLong(s, e)) {
                long[] ips = new long[2];
                ips[0] = s;
                ips[1] = e;
                vc.addElement(ips);
            } else {
                return null;
            }
        }
        return vc;
    }

    /**
     * @Title:splitSubIps
     * @Description TODO(输入被拆地址段，子地址段,合法地址段 返回拆分结果).
     * @date 2015年7月29日
     * @author luyao
     * @param sip
     *            long
     * @param eip
     *            long
     * @param subsip
     *            long
     * @param subeip
     *            long
     * @return Vector<?>
     */
    public static Vector<?> splitSubIps(long sip, long eip, long subsip,
            long subeip) {
        if ((sip > subsip) || (sip > subeip) || (eip < subsip)
                || (eip < subeip)) {
            return null;
        }
        return IPUtil.split(subsip, subeip, sip, eip, true);
    }

    /**
     * 
     * @Title:getIpAddress
     * @Description TODO(获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址;).
     * @date 2016年4月19日
     * @author yxx
     * @param request
     * @return
     * @throws IOException
     */
    public final static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            if (ip == null || ip.length() == 0
                    || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0
                    || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0
                    || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (ip == null || ip.length() == 0
                    || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_REAL_IP");
            }
            if (ip == null || ip.length() == 0
                    || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (ip == null || ip.length() == 0
                    || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
                // 本地访问转化
                if (ip.equals("127.0.0.1") || ip.equals("0:0:0:0:0:0:0:1")) {
                    // 根据网卡取本机配置的IP
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    ip = inet.getHostAddress();
                }
            }
        } else if (ip.length() > 15) {
            String[] ips = ip.split(",");
            for (int i = 0; i < ips.length; i++) {
                String strIp = (String) ips[i];
                if (!("unknown".equalsIgnoreCase(strIp))) {
                    ip = strIp;
                    break;
                }
            }
        }
        return ip;
    }

    /**
     * 判断内外网
     * 
     * @param request
     * @return
     */
    public static final Boolean isOutside(HttpServletRequest request) {
        Boolean result = false;
        // 获取客户端IP地址，考虑反向代理的问题
//        String ip = request.getHeader("x-forwarded-for");
//        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("Proxy-Client-IP");
//        }
//        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("WL-Proxy-Client-IP");
//        }
//        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getRemoteAddr();
//            if ("127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip)) {
//                InetAddress inet = null;
//                try {
//                    inet = InetAddress.getLocalHost();
//                    ip = inet.getHostAddress();
//                } catch (UnknownHostException e) {
//                    e.printStackTrace();
//                }
//            }
//        } 
        String ip = request.getRemoteAddr();
        if ("127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip)) {
            InetAddress inet = null;
            try {
                inet = InetAddress.getLocalHost();
                ip = inet.getHostAddress();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }

        if (!StringUtils.isEmpty(ip) && ip.length() > 15) {
            ip = ip.substring(0, ip.indexOf(","));
        }
        /*
         * 判断客户单IP地址是否为内网地址 内网IP网段： 10.0.0.0-10.255.255.255
         * 172.16.0.0-172.31.255.255 192.168.0.0-192.168.255.255
         */
        String reg = "^(192\\.168|172\\.(1[6-9]|2\\d|3[0,1]))(\\.(2[0-4]\\d|25[0-5]|[0,1]?\\d?\\d)){2}$|^10(\\.([2][0-4]\\d|25[0-5]|[0,1]?\\d?\\d)){3}$";
        Pattern p = Pattern.compile(reg);
        Matcher matcher = p.matcher(ip);
        result = matcher.find();

        return !result;
    }

    /**
     * @param ip
     * @return 通过IP获取MAC
     */
    public static String getMacAddrByIp(String ip) {
        String macAddr = null;
        try {
            Process process = Runtime.getRuntime().exec("nbtstat -a " + ip);
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            Pattern pattern = Pattern.compile("([A-F0-9]{2}-){5}[A-F0-9]{2}");
            Matcher matcher;
            for (String strLine = br.readLine(); strLine != null; strLine = br
                    .readLine()) {
                matcher = pattern.matcher(strLine);
                if (matcher.find()) {
                    macAddr = matcher.group();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(macAddr);
        return macAddr;
    }

    /**
     * 判断内外网
     * 
     * @param request
     * @return
     */
    public static final Boolean isOutsideByIP(String ip) {
        Boolean result = false;
        /*
         * 判断客户单IP地址是否为内网地址 内网IP网段： 10.0.0.0-10.255.255.255
         * 172.16.0.0-172.31.255.255 192.168.0.0-192.168.255.255
         */
        String reg = "^(192\\.168|172\\.(1[6-9]|2\\d|3[0,1]))(\\.(2[0-4]\\d|25[0-5]|[0,1]?\\d?\\d)){2}$|^10(\\.([2][0-4]\\d|25[0-5]|[0,1]?\\d?\\d)){3}$";
        Pattern p = Pattern.compile(reg);
        Matcher matcher = p.matcher(ip);
        result = matcher.find();
        return !result;
    }
}
