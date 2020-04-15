package com.hwsafe.demo.enums;

public enum SexEnum {
    MAN(1, "男"), WOMAN(2, "女");
    private int code;
    private String desc;

    private SexEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

}
