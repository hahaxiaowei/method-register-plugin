package com.huntkey.rx.sceo.method.register.plugin.entity;

import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 方法注册信息类
 */
public class MethodRegisterInfo {

    /**
     * 方法描述
     */
    private String methodDesc;

    /**
     * edm 类名称
     */
    private String edmClass;

    /**
     * 方法执行频率
     */
    private MethodExeFrequency methodExeFrequency;

    /**
     * 方法执行间隔
     */
    private int methodExeInterval;

    /**
     * 方法类型
     */
    private MethodType methodType;

    /**
     * 方法执行类型
     */
    private MethodExecType methodExecType;

    /**
     * 方法参数类型和名称
     * key : 参数名
     * val : 参数类型
     */
    private Map<String, String> paramsNameAndTypeMap;

    /**
     * 返回类型
     */
    private String returnType;

    /**
     * 请求访问路径
     */
    private String requestPath;

    /**
     * 服务名称
     */
    private String serviceApplicationName;

    /**
     * 方法名称
     */
    private String methodName;


    /**
     * 方法分类
     */
    private String methodCate;

    /**
     * 方法请求类型
     */
    private RequestMethod methodReqType;

    /**
     * 程序类型
     */
    private ProgramCate programCate;

    /**
     * get请求方法中非pathVariable 参数名
     */
    public String[] getReqParamsNameNoPathVariable;

    /**
     * 方法的状态
     *
     * @return
     */
    public MethodStatus methodStatus;

    /**
     * 方法的超时时间
     *
     * @return
     */
    public Integer timeout;

    public String[] getGetReqParamsNameNoPathVariable() {
        return getReqParamsNameNoPathVariable;
    }

    public void setGetReqParamsNameNoPathVariable(String[] getReqParamsNameNoPathVariable) {
        this.getReqParamsNameNoPathVariable = getReqParamsNameNoPathVariable;
    }

    public ProgramCate getProgramCate() {
        return programCate;
    }

    public void setProgramCate(ProgramCate programCate) {
        this.programCate = programCate;
    }

    public String getEdmClass() {
        return edmClass;
    }

    public void setEdmClass(String edmClass) {
        this.edmClass = edmClass;
    }

    public MethodExeFrequency getMethodExeFrequency() {
        return methodExeFrequency;
    }

    public void setMethodExeFrequency(MethodExeFrequency methodExeFrequency) {
        this.methodExeFrequency = methodExeFrequency;
    }

    public int getMethodExeInterval() {
        return methodExeInterval;
    }

    public void setMethodExeInterval(int methodExeInterval) {
        this.methodExeInterval = methodExeInterval;
    }

    public MethodType getMethodType() {
        return methodType;
    }

    public void setMethodType(MethodType methodType) {
        this.methodType = methodType;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public String getRequestPath() {
        return requestPath;
    }

    public void setRequestPath(String requestPath) {
        this.requestPath = requestPath;
    }

    public String getServiceApplicationName() {
        return serviceApplicationName;
    }

    public void setServiceApplicationName(String serviceApplicationName) {
        this.serviceApplicationName = serviceApplicationName;
    }

    public String getMethodCate() {
        return methodCate;
    }

    public void setMethodCate(String methodCate) {
        this.methodCate = methodCate;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    @Override
    public String toString() {
        return "MethodRegisterInfo{" +
                "methodDesc='" + methodDesc + '\'' +
                ", edmClass='" + edmClass + '\'' +
                ", methodExeFrequency=" + methodExeFrequency +
                ", methodExeInterval=" + methodExeInterval +
                ", methodType=" + methodType +
                ", methodExecType=" + methodExecType +
                ", paramsNameAndTypeMap=" + paramsNameAndTypeMap +
                ", returnType='" + returnType + '\'' +
                ", requestPath='" + requestPath + '\'' +
                ", serviceApplicationName='" + serviceApplicationName + '\'' +
                ", methodName='" + methodName + '\'' +
                ", methodCate='" + methodCate + '\'' +
                ", methodReqType=" + methodReqType +
                ", programCate=" + programCate +
                ", getReqParamsNameNoPathVariable=" + Arrays.toString(getReqParamsNameNoPathVariable) +
                ", methodStatus=" + methodStatus +
                ", timeout=" + timeout +
                '}';
    }

    public void setMethodReqType(RequestMethod methodReqType) {
        this.methodReqType = methodReqType;
    }

    public RequestMethod getMethodReqType() {
        return methodReqType;
    }

    public String getMethodDesc() {
        return methodDesc;
    }

    public void setMethodDesc(String methodDesc) {
        this.methodDesc = methodDesc;
    }

    public Map<String, String> getParamsNameAndTypeMap() {
        return paramsNameAndTypeMap;
    }

    public void setParamsNameAndTypeMap(Map<String, String> paramsNameAndTypeMap) {
        this.paramsNameAndTypeMap = paramsNameAndTypeMap;
    }

    public MethodExecType getMethodExecType() {
        return methodExecType;
    }

    public void setMethodExecType(MethodExecType methodExecType) {
        this.methodExecType = methodExecType;
    }

    public MethodStatus getMethodStatus() {
        return methodStatus;
    }

    public void setMethodStatus(MethodStatus methodStatus) {
        this.methodStatus = methodStatus;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }
}
