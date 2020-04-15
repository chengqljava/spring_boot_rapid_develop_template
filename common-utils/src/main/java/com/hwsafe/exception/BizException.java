package com.hwsafe.exception;

/**
 * 业务异常
 * 
 * @author chengql
 *
 */
public class BizException extends RuntimeException {

    private static final long serialVersionUID = -342132296305339699L;

    private Error error;

    public BizException(String messageTemplate, Object... messageArgs) {
        super(messageTemplate);
        this.error = Error.build("biz_error", messageTemplate, messageArgs);
    }

    public BizException(Error error) {
        this.error = error;
    }

    public BizException(String msg, Error error) {
        super(msg);
        this.error = error;
    }

    public Error getError() {
        return error;
    }

}
