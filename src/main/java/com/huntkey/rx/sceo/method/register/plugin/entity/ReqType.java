package com.huntkey.rx.sceo.method.register.plugin.entity;

import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by cjq on 2017/11/7 0003 上午 9:58
 */
public enum ReqType {

    //GET请求类型
    GET("GET", "查询", "0", RequestMethod.GET),
    //POST请求类型
    POST("POST", "增加", "1", RequestMethod.POST),
    //PUT请求类型
    PUT("PUT", "更新", "2", RequestMethod.PUT),
    //PATCH请求类型
    PATCH("PATCH", "局部更新", "3", RequestMethod.PATCH),
    //DELETE请求类型
    DELETE("DELETE", "删除", "4", RequestMethod.DELETE)
    ;

    private String reqType;
    private String dbType;
    private String typeCode;
    private RequestMethod requestMethod;


    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getReqType() {
        return reqType;
    }

    public void setReqType(String reqType) {
        this.reqType = reqType;
    }

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public RequestMethod getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(RequestMethod requestMethod) {
        this.requestMethod = requestMethod;
    }

    ReqType(String reqType, String dbType, String typeCode, RequestMethod requestMethod) {
        this.reqType = reqType;
        this.dbType = dbType;
        this.typeCode = typeCode;
        this.requestMethod = requestMethod;
    }

    public static ReqType getReqTypeByReqMethod(RequestMethod requestMethod){
        for(ReqType reqType : ReqType.values()){
            if(reqType.getRequestMethod() == requestMethod){
                return reqType;
            }
        }
        return null;
    }
}
