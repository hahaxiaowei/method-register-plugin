package com.huntkey.rx.sceo.method.register.plugin.util;

import lombok.Data;

/**
 * @author： sunwei17
 * @date： 2020/8/21 14:08
 * @description：
 * @modifiedBy：
 * @version: 1.0.0
 */
@Data
public class Result {

    public static Integer RECODE_SUCCESS = 0;
    public static Integer RECODE_ERROR = 1;
    public static Integer RECODE_UNLOGIN = -1;
    public static Integer RECODE_VALIDATE_ERROR = -2;
    public static String SUCCESS_MSG = "success";
    private String errMsg = "failed";
    private Integer retCode = RECODE_ERROR;
    private Object data;

    public String getErrMsg() {
        return errMsg;
    }

    public Integer getRetCode() {
        return retCode;
    }

    public Object getData() {
        return data;
    }
}
