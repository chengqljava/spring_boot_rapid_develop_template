package com.hwsafe.ottService.base.utils;

import java.io.Serializable;

import com.hwsafe.exception.ErrorTipTemplate;

import lombok.Data;

@Data
public class AppResponse implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = 6733421801454031269L;

    private Integer code;

    /** if(status==400)这里会存放相关错误信息 */
    private String msg;

    /** 具体的数据 */
    private Object result;
    /**
     * 当前时间
     */
    private Long systime;

    public AppResponse() {
    }

    public AppResponse(AppResponseStatus code, String msg, Object result) {
        this.code = code.statusCode;
        this.msg = msg;
        this.result = result;
        this.systime = System.currentTimeMillis();
    }

    public static AppResponse ok(String token, Object data) {
        return new AppResponse(AppResponseStatus.RS_OK, null, data);
    }

    public static AppResponse ok() {
        return new AppResponse(AppResponseStatus.RS_OK, null, null);
    }

    public static AppResponse ok(Object data) {
        return new AppResponse(AppResponseStatus.RS_OK, null, data);
    }

    public static AppResponse fail(String msg) {
        return new AppResponse(AppResponseStatus.RS_FAIL, msg, null);
    }

    public static AppResponse fail() {
        return new AppResponse(AppResponseStatus.RS_FAIL, "请求失败", null);
    }

    public static AppResponse param_invalid() {
        return new AppResponse(AppResponseStatus.RS_FAIL,
                ErrorTipTemplate.PARAMETER_NOT_NULL, null);
    }

    public static AppResponse param_invalid(String msg) {
        return new AppResponse(AppResponseStatus.RS_FAIL, msg, null);
    }

    public static AppResponse server_inner_error() {
        return new AppResponse(AppResponseStatus.RS_ERROR, "服务内部错误", null);
    }

    public static AppResponse token_expired() {
        return new AppResponse(AppResponseStatus.RS_TOKEN_INVALID, "token过期",
                null);
    }

    public static AppResponse token_error() {
        return new AppResponse(AppResponseStatus.RS_TOKEN_INVALID, "token错误",
                null);
    }

    public static AppResponse sign_error() {
        return new AppResponse(AppResponseStatus.RS_SIGN_ERROR, "sign错误", null);
    }

    public static AppResponse app_key_error() {
        return new AppResponse(AppResponseStatus.RS_APPKEY_ERROR, "appKey错误",
                null);
    }

    public static AppResponse app_param_error() {
        return new AppResponse(AppResponseStatus.RS_PARAM_ERROR, "参数错误", null);
    }

    public static AppResponse ip_error() {
        return new AppResponse(AppResponseStatus.RS_IP_ERROR, "ip不在白名单", null);
    }

    public static AppResponse error(AppResponseStatus appResponseStatus,
            String msg) {
        return new AppResponse(appResponseStatus, msg, null);
    }

    /**
     * RS_OK：200，业务逻辑正常且处理成功；请求正常，服务器正常，逻辑能够执行完毕，符合预期；<br/>
     * RS_FAIL：400，业务逻辑失败但处理成功；请求正常，服务器正常，逻辑执行后，不符合预期，如重复注册。 <br/>
     * RS_TOKEN_INVALID: 401，token失效，需要重新登录 <br/>
     * 402签名错误 RS_ERROR：500，服务器内部错误 <br/>
     * 
     * @author chengql
     * 
     */
    public enum AppResponseStatus {
        RS_OK(200), RS_FAIL(400), RS_TOKEN_INVALID(401), RS_SIGN_ERROR(
                402), RS_APPKEY_ERROR(403), RS_IP_ERROR(404), RS_PARAM_ERROR(
                        405), RS_ERROR(500), RS_APPVER_NONUPDATED(
                                2001), RS_APPVER_UPDATED(2000);

        private int statusCode;

        AppResponseStatus(int statusCode) {
            this.statusCode = statusCode;
        }

        public int statusCode() {
            return statusCode;
        }
    }

}
