package com.huntkey.rx.sceo.method.register.plugin.util;

import com.huntkey.rx.commons.utils.string.StringUtil;
import com.huntkey.rx.sceo.method.register.plugin.entity.*;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.xml.soap.SAAJResult;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sunwei on 2017/11/16 Time:15:59
 */
public class IsEqualUtil {

    /**
     * @param mri
     * @param edmMethodAndArgVO
     * @return java.lang.Boolean
     * @description 判断方法的所有信息是不是相同的
     * @method IsMethodEqual
     */
    public static Boolean isMethodEqual(MethodRegisterInfo mri, EdmMethodAndArgVO edmMethodAndArgVO) {

        EdmMethodVO vo = edmMethodAndArgVO.getEdmMethod_in();
        if (IsEqualUtil.isEqual(vo, mri) && IsEqualUtil.isParamsEqual(mri, edmMethodAndArgVO) && IsEqualUtil.isBothEqual(edmMethodAndArgVO.getEdmMethodreturn_in().getEdmaDataType(), dataType(mri.getReturnType()))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param first
     * @param second
     * @return java.lang.Boolean
     * @description 判断两个字符串是不是相同
     * @method IsBothEqual
     */
    public static Boolean isBothEqual(String first, String second) {

        if (!StringUtil.isNullOrEmpty(first) && !StringUtil.isNullOrEmpty(second)) {
            if (first.equals(second)) {
                return true;
            } else {
                return false;
            }
        } else {
            if (StringUtil.isNullOrEmpty(first) && StringUtil.isNullOrEmpty(second)) {
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * @param vo
     * @param mri
     * @return java.lang.Boolean
     * @description 判断方法的传入的属性和EDM里面的属性是不是相同
     * @method isEqual
     */
    public static Boolean isEqual(EdmMethodVO vo, MethodRegisterInfo mri) {
        //判断方法分类名
        if (!IsEqualUtil.isBothEqual(vo.getEdmmTypeName(), mri.getMethodCate())) {
            return false;
        }
        //判断方法所属类名
        else if (!IsEqualUtil.isBothEqual(vo.getEdmcName(), mri.getEdmClass())) {
            return false;
        }
        //方法描述
        else if (!IsEqualUtil.isBothEqual(vo.getEdmmDesc(), mri.getMethodDesc())) {
            return false;
        }
        //程序类型
        else if (!IsEqualUtil.isBothEqual(vo.getEdmmProgramType(), mri.getProgramCate().getnCode())) {
            return false;
        }
        //方法类型
        else if (!IsEqualUtil.isBothEqual(vo.getEdmmMethodType(), mri.getMethodType().getType())) {
            return false;
        }
        //方法执行类型
        else if (!IsEqualUtil.isBothEqual(vo.getEdmmExecuteType(), mri.getMethodExecType().getType())) {
            return false;
        }
        //算法描述
        else if (!IsEqualUtil.isBothEqual(vo.getEdmmArithmeticDesc(), methodFullUrl(mri))) {
            return false;
        }
        //方法的请求类型
        else if (!IsEqualUtil.isBothEqual(vo.getEdmmRequestType(), ReqType.getReqTypeByReqMethod(mri.getMethodReqType()).getReqType())) {
            return false;
        }
        //执行频率
        else if (!IsEqualUtil.isBothEqual(vo.getEdmmExecRate(), mri.getMethodExeFrequency().getRate())) {
            return false;
        }
        //方法的状态
        else if (!IsEqualUtil.isBothEqual(vo.getEdmmStatus(), mri.getMethodStatus().getStatusCode())) {
            return false;
        }
        //同步和异步方法的数据读取超时时间
        else if (!IsEqualUtil.isBothEqual(String.valueOf(vo.getTimeout()), String.valueOf(mri.getTimeout()))) {
            return false;
        }
        return true;
    }


    /**
     * @param mri
     * @return java.lang.String
     * @description 拼装接口完整的url
     * @method methodFullUrl
     */
    public static String methodFullUrl(MethodRegisterInfo mri) {

        StringBuffer url = new StringBuffer();
        url.append("http://");
        url.append(mri.getServiceApplicationName());
        url.append(mri.getRequestPath());
        if (mri.getReqParamsNameNoPathVariable.length == 0 || mri.getMethodReqType() != RequestMethod.GET) {
            return url.toString();
        }
        Boolean flag = true;
        for (String param : mri.getReqParamsNameNoPathVariable) {
            if (StringUtil.isNullOrEmpty(param)) {
                continue;
            }
            if (flag) {
                url.append("?");
                flag = false;
            } else {
                url.append("&");
            }
            url.append(param + "={" + param + "}");
        }
        return url.toString();
    }

    /**
     * @param str
     * @return java.util.Map<java.lang.Integer,java.lang.String>
     * @description 分离字符串里面的参数
     * @method split
     */
    public static Map<Integer, String> split(String[] str) {

        Map<Integer, String> map = new HashMap<Integer, String>();
        for (int i = 0; i < str.length; i++) {
            map.put(i + 1, str[i]);
        }
        return map;
    }


    /**
     * @param mri
     * @param edmMethodAndArgVO
     * @return java.lang.Boolean
     * @description 判断EDM里面的参数以及参数类型，是不是和注解传来的参数以及参数对应的数据类型是不是相等
     * @method IsParamsEqual
     */
    public static Boolean isParamsEqual(MethodRegisterInfo mri, EdmMethodAndArgVO edmMethodAndArgVO) {

        List<EdmMethodArg> list = edmMethodAndArgVO.getEdmMethodInsertList_in();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getEdmaType() == 2) {
                list.remove(i);
            }
        }
        Map<String, String> map = mri.getParamsNameAndTypeMap();
        Boolean tag = true;
        if (!StringUtil.isNullOrEmpty(list) && !StringUtil.isNullOrEmpty(map)) {

            if (list.size() != map.size()) {
                tag = false;
            } else {
                for (EdmMethodArg edmMethodArg : list) {
                    String obj = map.get(edmMethodArg.getEdmaName());
                    if (!isBothEqual(edmMethodArg.getEdmaDataType(), dataType(String.valueOf(obj)))) {
                        tag = false;
                        break;
                    }
                }
            }
        } else {
            if (StringUtil.isNullOrEmpty(list) && StringUtil.isNullOrEmpty(map)) {
                tag = true;
            } else {
                tag = false;
            }
        }
        return tag;
    }

    /**
     * @param dataType
     * @return java.lang.String
     * @description 判断参数的数据类型，输入参数的字符串
     * @method DataType
     */
    public static String dataType(String dataType) {
        return DataType.getDateType(dataType).getEmdDateTypeCode();
    }
}
