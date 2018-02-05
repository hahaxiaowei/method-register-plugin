package com.huntkey.rx.sceo.method.register.plugin.entity;

public enum MethodType {

    // 一般方法
    GeneralMethod("一般方法", "0"),

    // 联动方法
    ConnectMethod("联动方法", "1"),

    // 卷积方法
    FoldMethod("卷积方法", "2"),;


    private String name;
    private String type;

    MethodType(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

}
