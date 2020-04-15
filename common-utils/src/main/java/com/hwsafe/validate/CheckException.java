package com.hwsafe.validate;

import org.apache.commons.lang3.StringUtils;

import com.hwsafe.exception.Error;

public class CheckException extends RuntimeException {

    /**  */
    private static final long serialVersionUID = 1L;

    private Error error;

    public CheckException() {
        super();
    }

    public CheckException(String messageTemplate, Object... messageArgs) {
        super(messageTemplate);
        if (StringUtils.isBlank(messageTemplate))
            messageTemplate = "error.illegalArgument";
        this.error = Error.build("param_error", messageTemplate, messageArgs);
    }

    public CheckException(Error error) {
        this.error = error;
    }

    public CheckException(String msg, Error error) {
        super(msg);
        this.error = error;
    }

    public Error getError() {
        return error;
    }

}
