package com.hwsafe.utils;

import java.io.Serializable;

/**
 * @author chengql
 * 
 * @param <T>
 *            返回数据
 * 
 */
public class AjaxResult<T> implements Serializable {

    /**  */
    private static final long serialVersionUID = -3689014703548996975L;

    private boolean success;
    private String errorMsg;
    private T value;

    public AjaxResult(T value) {
        super();
        this.success = true;
        this.value = value;
    }

    public AjaxResult(boolean success, T value) {
        super();
        this.success = success;
        this.value = value;
    }

//	public AjaxResult(boolean success, String errorMsg) {
//		super();
//		this.success = success;
//		this.errorMsg = errorMsg;
//	}

    public AjaxResult() {
        this.success = true;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
        this.success = false;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

}
