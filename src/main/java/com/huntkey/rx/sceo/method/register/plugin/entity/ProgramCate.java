package com.huntkey.rx.sceo.method.register.plugin.entity;

/**
 * Created by cjq on 2017/11/8 0003 上午 9:58
 */
public enum ProgramCate {

    //SQL基本代码
    SQL ("1"),

    //java代码
    Java("2"),

    //其他类型
    others("3");

    private String nCode ;

    private ProgramCate(String nCode) {
        this . nCode = nCode;
    }

    public String getnCode() {
        return nCode;
    }
}
