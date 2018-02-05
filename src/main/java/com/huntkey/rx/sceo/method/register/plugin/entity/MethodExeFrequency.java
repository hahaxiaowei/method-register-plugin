package com.huntkey.rx.sceo.method.register.plugin.entity;

/**
 * Created by cjq on 2017/11/7 0003 上午 9:58
 */
public enum MethodExeFrequency {

    //执行一次
    Once("0", "一次"),

    //执行多次
    Loop("1", "多次")
    ;

    private String rate;
    private String rateStr;

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getRateStr() {
        return rateStr;
    }

    public void setRateStr(String rateStr) {
        this.rateStr = rateStr;
    }

    MethodExeFrequency(String rate, String rateStr) {
        this.rate = rate;
        this.rateStr = rateStr;
    }
}
