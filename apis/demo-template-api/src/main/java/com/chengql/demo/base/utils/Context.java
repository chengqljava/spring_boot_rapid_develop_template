package com.hwsafe.demo.base.utils;

public class Context {

    private static ThreadLocal<String> APP_TOKEN = new ThreadLocal<String>();

    public static void setSysUser(String user) {
        APP_TOKEN.set(user);
    }

    public static String getSysUser() {
        return APP_TOKEN.get();
    }

    public static void remove() {
        APP_TOKEN.remove();
    }

}
