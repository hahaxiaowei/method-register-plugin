package com.huntkey.rx.sceo.method.register.plugin.entity;

/**
 * Created by sunwei on 2017/11/13 Time:14:15
 */
public enum MethodStatus {
    //方法启用
    Enable("1"),

    //方法禁用
    Disable("0");

    private String statusCode;

    MethodStatus(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }
}
