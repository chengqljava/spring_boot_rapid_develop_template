package com.hwsafe.common;

/**
 * 网络通讯协议枚举类
 * 
 * @author liuxb
 * @date 2019-12-12
 */
public enum NetworkProtocolEnum {
    /**
     * http：http协议 https： https协议
     */
    HTTP("http", "http协议"), HTTPS("https", "https协议");

    private String code;
    private String desc;

    private NetworkProtocolEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static NetworkProtocolEnum codes(String code) {
        for (NetworkProtocolEnum networkProtocolEnum : NetworkProtocolEnum
                .values()) {
            if (code.equals(networkProtocolEnum.getCode())) {
                return networkProtocolEnum;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

}
