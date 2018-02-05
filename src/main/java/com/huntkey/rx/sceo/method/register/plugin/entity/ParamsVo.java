package com.huntkey.rx.sceo.method.register.plugin.entity;

import java.io.Serializable;

/**
 * Created by lulx on 2017/10/17 0017 上午 9:42
 */
public class ParamsVo implements Serializable {
    private static final long serialVersionUID = 7712232177983902842L;
    private String className;
    private String methodName;
    /**
     * 自动方法执行所需的数据ID
     */
    private String dataId;
    private Object paramObj;

    /**
     * 定时方法判断条件：创建时间
     */
    private String createTime;

    private String authorization;

    private String bizDriverIP;

    public ParamsVo() {
    }

    public ParamsVo(String className, String methodName, String dataId, String createTime, Object paramObj) {
        this.className = className;
        this.methodName = methodName;
        this.dataId = dataId;
        this.paramObj = paramObj;
        this.createTime = createTime;
    }

    public ParamsVo(String className, String methodName, String dataId, Object paramObj, String createTime, String authorization) {
        this.className = className;
        this.methodName = methodName;
        this.dataId = dataId;
        this.paramObj = paramObj;
        this.authorization = authorization;
        this.createTime = createTime;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object getParamObj() {
        return paramObj;
    }

    public void setParamObj(Object paramObj) {
        this.paramObj = paramObj;
    }

    @Override
    public String toString() {
        return "ParamsVo{" +
                "className='" + className + '\'' +
                ", methodName='" + methodName + '\'' +
                ", dataId='" + dataId + '\'' +
                ", paramObj=" + paramObj +
                ", createTime='" + createTime + '\'' +
                ", authorization='" + authorization + '\'' +
                '}';
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public String getBizDriverIP() {
        return bizDriverIP;
    }

    public void setBizDriverIP(String bizDriverIP) {
        this.bizDriverIP = bizDriverIP;
    }
}

