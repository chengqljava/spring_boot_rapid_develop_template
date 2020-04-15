package com.hwsafe.exception;

/**
 * 系统异常
 * 
 */
public class SystemException extends RuntimeException {

    /** serialVersionUID */
    private static final long serialVersionUID = -9004315216203106175L;

    private String messageTemplate;
    private Object[] messageArgs;

    public SystemException(String messageTemplate) {
        this.messageTemplate = messageTemplate;
    }

    public SystemException(String messageTemplate, Object[] messageArgs) {
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
