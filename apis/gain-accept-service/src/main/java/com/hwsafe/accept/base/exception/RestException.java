package com.hwsafe.accept.base.exception;

public class RestException extends RuntimeException {

    /** serialVersionUID */
    private static final long serialVersionUID = 2367834389342783205L;

    public RestException() {
    }

    public RestException(String message) {
        super(message);
    }

}
