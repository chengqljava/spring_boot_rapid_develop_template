package com.hwsafe.weixin.template;

import java.util.Map;

/**
 * 模板消息发送bean
 * 
 * @author Administrator
 *
 */
public class TemplateSendBean {
    /**
     * 手机号
     */
    private String touser;
    /**
     * 发送类型 {WX10086Utils.sceneType}
     */
    private String sceneType;
    /**
     * 省份编码
     */
    private String provinceCode;
    /**
     * 模板id
     */
    private String template_id;
    /**
     * 跳转url
     */
    private String url;
    /**
     * 发送data json数据
     */
    private Map<String, TemplateBean> data;

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getSceneType() {
        return sceneType;
    }

    public void setSceneType(String sceneType) {
        this.sceneType = sceneType;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, TemplateBean> getData() {
        return data;
    }

    public void setData(Map<String, TemplateBean> data) {
        this.data = data;
    }

}
