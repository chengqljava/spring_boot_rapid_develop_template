/**  
 * Project Name:module_system  
 * File Name:HttpTool.java  
 * Package Name:com.zwsafety.module.system.utils  
 * Date:2017年6月1日
 * Copyright (c) 2017,zwsafety All Rights Reserved.   
 */

package com.hwsafe.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;

/**
 * @ClassName:HttpMessage
 * @Description:TODO(短信发送工具类)
 * @date:2017年6月1日
 * @author Administrator
 * @version 1.0
 * @since: JDK 1.7
 */
public class HttpMessage {
    /**
     * 获取20位数字序列号
     * 
     * @return
     */
    public static String getSerialNo() {
        Long now = System.currentTimeMillis();
        // 获取4位年份数字
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
        // 获取时间戳
        String time = dateFormat.format(now);
        // 获取三位随机数
        int ran = (int) ((Math.random() * 9 + 1) * 100);
        return time + now + ran;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @param isproxy
     *            是否使用代理模式
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        OutputStreamWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            HttpURLConnection conn = null;
            conn = (HttpURLConnection) realUrl.openConnection();
            // 打开和URL之间的连接

            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST"); // POST方法
            // 设置通用的请求属性

            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");

            conn.connect();

            // 获取URLConnection对象对应的输出流
            out = new OutputStreamWriter(conn.getOutputStream(), "GBK");
            // 发送请求参数
            out.write(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        System.out.println(result);
        return result;
    }

    /**
     * 
     * @Title:sendMessage
     * @Description TODO(发送短信接口，如果成功返回true). TODO(这里描述这个方法适用条件 – 可选).
     *              TODO(这里描述这个方法的执行流程 – 可选). TODO(这里描述这个方法的使用方法 – 可选).
     *              TODO(这里描述这个方法的注意事项 – 可选).
     * @date 2017年6月1日
     * @author Administrator
     * @param mobile
     *            手机号
     * @param content
     *            短信内容
     * @return
     */
    public boolean sendMessage(String mobile, String content, String smsSpCode,
            String smsLoginName, String smsPassword, String smsMessageUrl) {
//        String smsSpCode = ResourceUtil.getConfigByName("smsSpCode");
//        String smsLoginName = ResourceUtil.getConfigByName("smsLoginName");
//        String smsPassword = ResourceUtil.getConfigByName("smsPassword");
//        String smsMessageUrl = ResourceUtil.getConfigByName("smsMessageUrl");
        String params = "SpCode=" + smsSpCode + "&LoginName=" + smsLoginName
                + "&Password=" + smsPassword + "&MessageContent=" + content
                + "&UserNumber=" + mobile + "&SerialNumber=" + getSerialNo();
        String results = sendPost(smsMessageUrl, params);
//        String results = sendPost("https://api.ums86.com:9600/sms/Api/Send.do","SpCode=209005&LoginName=sh_zwta&Password=qpaj624152&MessageContent=您的验证码为342221&UserNumber=18612203700&SerialNumber="+getSerialNo()+"&ScheduleTime=&f=1");
        // 形如result=32&description=发送成功，解析字符
        String[] result = results.split("&");
        String words = "1";// 默认为1失败
        boolean isSuccess = false;
        if (result.length > 0) {
            words = result[0].split("=")[1];
        }
        // 如果result返回0 则发送成功
        if (!"".equals(words) && "0".equals(words)) {
            isSuccess = true;
        }
        return isSuccess;
    }

    public static void main(String[] args) {
        HttpMessage m = new HttpMessage();
//        m.sendMessage("18612203700","您的验证码为：123456");
//        sendPost("https://api.ums86.com:9600/sms/Api/Send.do","SpCode=209005&LoginName=sh_zwta&Password=qpaj624152&MessageContent=你有一项编号为123456789的事务需要处理。&UserNumber=18612203700&SerialNumber="+getSerialNo()+"&ScheduleTime=&f=1");
    }
}
