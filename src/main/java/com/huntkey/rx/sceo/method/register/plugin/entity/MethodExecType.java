package com.huntkey.rx.sceo.method.register.plugin.entity;

/**
 * Created by lulx on 2017/12/8 0008 上午 10:45
 */
public enum  MethodExecType {
    //同步类型
    SyncMethod("同步", "0"),

    //异步类型
    AsyncMethod("异步", "1"),

    //自动类型
    AutoMethod("自动", "2"),

    //定时类型
    TimingMethod("定时", "3"),;


    private String name;
    private String type;

    MethodExecType(String name, String type) {
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
