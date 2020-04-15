package com.hwsafe.exception;

/**
 * 视频智能分析异常
 * 
 */
public class MacAlarmVideoException extends RuntimeException {

    /** serialVersionUID */
    private static final long serialVersionUID = -9004315216203106175L;

    private String messageTemplate;
    private Object[] messageArgs;

    public MacAlarmVideoException(String messageTemplate) {
        this.messageTemplate = messageTemplate;
    }

    public MacAlarmVideoException(String messageTemplate,
            Object[] messageArgs) {
        this.messageTemplate = messageTemplate;
        this.messageArgs = messageArgs;
    }

    public String getMessageTemplate() {
        return messageTemplate;
    }

    public Object[] getMessageArgs() {
        return messageArgs;
    }
}
