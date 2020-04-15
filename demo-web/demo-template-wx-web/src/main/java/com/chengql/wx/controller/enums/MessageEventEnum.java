package com.hwsafe.wx.controller.enums;

public enum MessageEventEnum {

    MESSAGE_SUBSCIBE("subscribe", "消息事件类型--订阅事件"), MESSAGE_UNSUBSCIBE(
            "unsubscribe",
            "消息事件类型--取消订阅事件"), MESSAGE_LOCATION("LOCATION", "地理信息");
    private String code;
    private String desc;

    private MessageEventEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static MessageEventEnum codes(String event) {
        for (MessageEventEnum messageEvent : MessageEventEnum.values()) {
            if (messageEvent.getCode().equals(event)) {
                return messageEvent;
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
