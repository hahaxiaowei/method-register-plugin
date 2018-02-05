package com.huntkey.rx.sceo.method.register.plugin.entity;

/**
 * Created by lulx on 2017/11/24 0024 上午 10:51
 */
public enum DataType {
    STRING("string", "1", "java.lang.String"),
    INT("int", "2", "int"),
    INTEGER("int", "2", "java.lang.Integer"),
    BOOL("bool", "4", "boolean"),
    BOOLEAN("bool", "4", "java.lang.Boolean"),
    DOUBLE("double", "3", "java.lang.Double"),
    DOU("double", "3", "double"),
    DATE("date", "5", "java.util.Date"),
    LIST("list", "6", "java.util.List"),
    STRINGS("string[]", "7", "[Ljava.lang.String;"),
    INTS("int[]", "8", "[I"),
    INTEGERS("int[]", "8", "[Ljava.lang.Integer;"),
    DOUBLES("double[]", "9", "[Ljava.lang.Double;"),
    DOUs("double[]", "9", "[D"),
    RESULT("Result", "10", "com.huntkey.rx.commons.utils.rest.Result"),
    OBJECT("Object", "11", "java.lang.Object"),
    ;

    /**
     * 判断输入参数或者返回值参数的数据类型
     * @param dateType
     * @return
     */
    public static DataType getDateType(String dateType){
        for (DataType e: DataType.values()) {
            if(e.getDateType().equalsIgnoreCase(dateType)){
                return e;
            }
        }
        return DataType.OBJECT;
    }

    private String edmDataTyep;
    private String emdDateTypeCode;
    private String dateType;

    DataType(java.lang.String edmDataTyep, java.lang.String emdDateTypeCode, java.lang.String dateType) {
        this.edmDataTyep = edmDataTyep;
        this.emdDateTypeCode = emdDateTypeCode;
        this.dateType = dateType;
    }

    public java.lang.String getEdmDataTyep() {
        return edmDataTyep;
    }

    public void setEdmDataTyep(java.lang.String edmDataTyep) {
        this.edmDataTyep = edmDataTyep;
    }

    public java.lang.String getEmdDateTypeCode() {
        return emdDateTypeCode;
    }

    public void setEmdDateTypeCode(java.lang.String emdDateTypeCode) {
        this.emdDateTypeCode = emdDateTypeCode;
    }

    public java.lang.String getDateType() {
        return dateType;
    }

    public void setDateType(java.lang.String dateType) {
        this.dateType = dateType;
    }
}
