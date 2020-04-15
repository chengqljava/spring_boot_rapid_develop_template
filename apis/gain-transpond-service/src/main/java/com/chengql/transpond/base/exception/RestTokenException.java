package com.hwsafe.transpond.base.exception;

/**
 * Token错误
 * 
 * @author chengql
 * @version $Id: RestTokenException.java, v 0.1 2015年12月9日 下午2:32:26 chengql Exp
 *          $
 */
public class RestTokenException extends RuntimeException {
    /**  */
    private static final long serialVersionUID = -8764223215790198162L;

    /** serialVersionUID */

    public RestTokenException() {
    }

    public RestTokenException(String message) {
        super(message);
    }
}
