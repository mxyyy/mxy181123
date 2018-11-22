package com.bwie.mxy181123.utils;

public class Https2http {

    /**
     * 接口https替换
     * @param url
     * @return
     */
    public static String replace(String url) {
        return url.replace("https", "http");
    }
}
