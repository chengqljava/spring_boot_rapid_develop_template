package com.hwsafe.accept.base.exception;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.hwsafe.accept.base.utils.AppResponse;
import com.hwsafe.validate.CheckException;

@ControllerAdvice
public class RestExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

    /**
     * 处理RestException.
     */
    @ExceptionHandler(value = { RestException.class })
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public final AppResponse handleException(RestException ex,
            HttpServletRequest request) {

        logger.warn(request.getRequestURI() + " 发生RestException", ex);

        return AppResponse.fail(ex.getMessage());
    }

    /**
     * 处理RestTokenException.
     */
    @ExceptionHandler(value = { RestTokenException.class })
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public final AppResponse handleException(RestTokenException ex,
            HttpServletRequest request) {

        logger.warn(request.getRequestURI() + " 发生RestTokenException", ex);

        return AppResponse.token_error();
    }

    /**
     * 处理RestTokenExpireException.
     */
    @ExceptionHandler(value = { RestTokenExpireException.class })
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public final AppResponse handleException(RestTokenExpireException ex,
            HttpServletRequest request) {

        logger.warn(request.getRequestURI() + " 发生RestTokenExpireException",
                ex);

        return AppResponse.token_expired();
    }

    /**
     * 处理HttpRequestMethodNotSupportedException.
     */
    @ExceptionHandler(value = { HttpRequestMethodNotSupportedException.class })
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public final AppResponse handleException(
            HttpRequestMethodNotSupportedException ex,
            HttpServletRequest request) {

        logger.warn(request.getRequestURI()
                + " 发生HttpRequestMethodNotSupportedException", ex);

        return AppResponse.fail(ex.getMessage());
    }

    /**
     * 处理ParameterException.
     */
    @ExceptionHandler(value = { CheckException.class })
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public final AppResponse handleException(CheckException ex,
            HttpServletRequest request) {

        logger.warn(request.getRequestURI() + " 发生CheckException", ex);

        return AppResponse.fail(ex.getMessage());
    }

    @ExceptionHandler(value = { IllegalArgumentException.class })
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public final AppResponse handleIllegalArgumentException(
            HttpServletRequest request, IllegalArgumentException ex) {

        logger.warn(request.getRequestURI() + " 发生IllegalArgumentException",
                ex);

        return AppResponse.param_invalid();
    }

    /**
     * 处理Exception.
     */
    @ExceptionHandler(value = { Exception.class })
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public final AppResponse handleException(Exception ex,
            HttpServletRequest request) {

        logger.error(request.getRequestURI() + "发生未知异常", ex);

        return AppResponse.server_inner_error();
    }
}
