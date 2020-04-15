package com.hwsafe.weixin.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.hwsafe.utils.IDGenerator;
import com.hwsafe.weixin.template.TemplateSendBean;
import com.hwsafe.weixin.utils.Constants;
import com.hwsafe.weixin.utils.JsonUtils;
import com.hwsafe.weixin.utils.TxtUtils;
import com.hwsafe.weixin.utils.WXSendUtils;

@Controller
@RequestMapping(value = "/manager")
public class WxSendController {
    private static final Logger logger = LoggerFactory
            .getLogger(WxSendController.class);
    // 消息推送明细表
    // 消息推送统计表
    // 微信推送微信id表

    @RequestMapping(value = "/sendTemplate", method = { RequestMethod.POST,
            RequestMethod.GET })
    @ResponseBody
    public Map<String, Object> sendTemplate(@RequestBody TemplateSendBean tsb) {
        // 微信推送
        Map<String, Object> result = new HashMap<String, Object>();
        FileInputStream in = null;
        try {
            in = new FileInputStream(new File(tsb.getTouser()));
            // 读取文件获取手机号集合
            List<String[]> mobileList = TxtUtils.readTxt(in,
                    Constants.Charset.UTF8);
            // 需要推送的手机号码数
            int count = mobileList.size();

            String uuid = IDGenerator.OBJECTID.generate();
            int success = 0;
            int failure = 0;
            int number = 0;
            // 存放发送记录
            List<T> sendmsgList = new ArrayList<T>();

            for (int i = 0; i < count; i++) {
                // 取待发送的记录
                String[] mobile = mobileList.get(i);

                long startTime = System.currentTimeMillis();// 开始时间
                // 取当前小时
                SimpleDateFormat dateFormater = new SimpleDateFormat("HH");
                // 小时格式转化位整形
                int formatDate = new Integer(dateFormater.format(new Date()));

                tsb.setTouser(mobile[0]);

                String sst = "";
                // 保存消息推送明细表
                try {
                    // 如果当前时间在8点以前或者20点以后，不执行发送
                    if (formatDate < 8 || formatDate > 19) {
                        logger.warn("no auth timezone userPhone:" + mobile[0]);// time
                                                                               // zone
                                                                               // error!
                    } else {
                        sst = WXSendUtils.sendTemplateMsg(
                                JsonUtils.convertObject2Json(tsb));
                        // logger.info("微信推送返回的结果:" + sst);
                        JSONObject json = JSONObject.parseObject(sst);// {"errcode":0,"errmsg":"ok","msgid":1222176073266429952}
                        int status = (int) json.get("errcode");
                        String message = (String) json.get("errmsg");
                        // 判断是否成功
                        if (0 == status) {
                            // 成功
                            success++;
//						logger.info("微信推送成功"+mobile[0]);
                        } else {
                            // 失败
                            failure++;
                            logger.info("微信推送失败" + mobile[0] + "-" + status
                                    + "-" + message);
                        }
                    }

                } catch (Throwable e) {
                    // 失败
                    failure++;
                    logger.error("调用推送接口失败" + mobile[0], e);
                }
                number++;
//				sendmsgList.add();
                try {
                    if (number == 2000 || i == count - 1) {
                        // 保存操作
                        sendmsgList.clear();// 清空list集合
                        number = 0;
                    }
                } catch (Exception e) {
                    logger.error("微信推送明细表保存出错", e);
                }

                long endTime = System.currentTimeMillis();// 结束时间
                logger.info("当前耗时:" + (endTime - startTime) + "ms");
            }
            //// 消息推送统计表
            // 计算成功率 成功条数 除以 总条数
            String rate = division(success, count);
        } catch (Exception e) {
            logger.error("一键发布失败", e);
            result.put("002", "失败");
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                logger.error("关闭文件流出错", e);
            }
        }
        result.put("001", "成功");
        result.put("datMsg", "sst");
        return result;
    }

    /**
     * 计算成功率的方法
     * 
     * @param a
     *            成功条数
     * @param b
     *            总条数
     * @return
     */
    public static String division(int a, int b) {
        String result = "";
        float num = (float) a / b * 100;
        DecimalFormat df = new DecimalFormat("0.00");
        result = df.format(num);
        return result;

    }

}
