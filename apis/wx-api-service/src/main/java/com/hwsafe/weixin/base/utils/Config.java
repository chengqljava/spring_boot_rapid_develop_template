package com.hwsafe.weixin.base.utils;

import java.util.HashMap;
import java.util.Map;

public class Config {

    /** appKey,appSecret映射 */
    private static Map<String, String> SECRET_KEY_MAP = new HashMap<String, String>();
    /** token是否需要验证过期 */
    private static boolean TOKEN_EXPIRED_SWITCH = false;
    /** token过期时间，分钟 */
    private static int TOKEN_EXPIRED_MINUTES = 30;
    /** 时间戳过期秒数，小于等于0表示不过期 */
    private static int TIMESTAMP_OUT_SECONDS = -1;

    private final static Config INSTANCE = new Config();

    private Config() {
        // TODO 时间紧以后改为配置参数
        SECRET_KEY_MAP.put("app_key", "app_secret");
        SECRET_KEY_MAP.put("app_secret", "appSecret");
    }

    public static Config getInstance() {
        return INSTANCE;
    }

    public Map<String, String> getSECRET_KEY_MAP() {
        return SECRET_KEY_MAP;
    }

    public void setSECRET_KEY_MAP(Map<String, String> sECRET_KEY_MAP) {
        SECRET_KEY_MAP = sECRET_KEY_MAP;
    }

    public void setTOKEN_EXPIRED_SWITCH(boolean tOKEN_EXPIRED_SWITCH) {
        TOKEN_EXPIRED_SWITCH = tOKEN_EXPIRED_SWITCH;
    }

    public void setTOKEN_EXPIRED_MINUTES(int tOKEN_EXPIRED_MINUTES) {
        TOKEN_EXPIRED_MINUTES = tOKEN_EXPIRED_MINUTES;
    }

    public void setTIMESTAMP_OUT_SECONDS(int tIMESTAMP_OUT_SECONDS) {
        TIMESTAMP_OUT_SECONDS = tIMESTAMP_OUT_SECONDS;
    }

}
