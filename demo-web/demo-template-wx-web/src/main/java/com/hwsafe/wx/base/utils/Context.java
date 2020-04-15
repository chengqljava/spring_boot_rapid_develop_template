package com.hwsafe.wx.base.utils;

public class Context {

    private static ThreadLocal<String> USER = new ThreadLocal<String>();

    public static void setUser(String user) {
        USER.set(user);
    }

    public static String getUser() {
        return USER.get();
    }

    public static void remove() {
        USER.remove();
    }

}
