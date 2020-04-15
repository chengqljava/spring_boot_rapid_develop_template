package com.hwsafe.weixin.utils;

import org.springframework.beans.factory.annotation.Value;

public class WXSendUtils {
    // 从配置文件中取域名
    @Value("${wx.domainPrefix}")
    private String domainPrefix;

    @Value("${wx.wxtoken}")
    private String token;

//    protected static String sendTemplateMsg = domainPrefix + "/api/out/templateMessage?appId=APPID&timestamp=TIMESTAMP&nonce=NONCE&signature=SIGNATURE";
    protected static String sendTemplateMsg = "https://api.weixin.qq.com"
            + "/cgi-bin/message/template/send?access_token="
            + "30_5AOcLcUuT3bjBU2nw5D5Vr5lxlzOhVdALdOGRG8tgPEndTHvfa8xUJpiTFnFgyrpnyRrAUT1xIcrG2BKagnkz4s_SA0P0G1ONJemvk7lKvkrXnSiljq5z7dSaX6Tc8DWDck09MbilJCqB3NfPQXhAEADEL";

    public static String sendTemplateMsg(String data) {
        String url = sendTemplateMsg;
        return HttpKit.post(url, data);
    }

}
