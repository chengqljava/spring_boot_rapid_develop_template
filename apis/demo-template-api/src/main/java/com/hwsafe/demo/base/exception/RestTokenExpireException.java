package com.hwsafe.demo.base.exception;

/**
 * Token 过期
 * 
 * @author chengql
 * @version $Id: RestTokenExpireException.java, v 0.1 2015年12月9日 下午2:33:01
 *          chengql Exp $
 */
public class RestTokenExpireException extends RuntimeException {
    /** serialVersionUID */
    private static final long serialVersionUID = 631701419920210985L;

    public RestTokenExpireException() {
    }

    public RestTokenExpireException(String message) {
        super(message);
    }
}
