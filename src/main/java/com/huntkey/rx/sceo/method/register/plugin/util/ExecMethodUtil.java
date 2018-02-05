package com.huntkey.rx.sceo.method.register.plugin.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huntkey.rx.commons.utils.string.StringUtil;
import com.huntkey.rx.sceo.method.register.plugin.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.transform.Result;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by sunwei on 2017/11/21 Time:11:58
 */
public class ExecMethodUtil {

    private static Logger logger = LoggerFactory.getLogger(ExecMethodUtil.class);

    /**
     * @param edmServiceName
     * @param mri
     * @return com.huntkey.rx.sceo.method.register.plugin.entity.EdmMethodAndArgVO
     * @description 方法插入
     * @method insertEdm
     */
    public static EdmMethodAndArgVO insertEdm(String edmServiceName, MethodRegisterInfo mri) {

        EdmMethodAndArgVO ev = new EdmMethodAndArgVO();
        EdmMethodVO ed = new EdmMethodVO();
        List<EdmMethodArg> list = new ArrayList<EdmMethodArg>();
        List<String> params = new ArrayList<String>();
        Map<String, String> map = mri.getParamsNameAndTypeMap();
        try {
            String addUrl = "http://" + edmServiceName + "/methods/";
            //取出参数列表
            for (Map.Entry<String, String> entry : map.entrySet()) {
                params.add(entry.getKey());
            }
            for (int i = 0; i < map.size(); i++) {
                EdmMethodArg edmMethodArg = new EdmMethodArg();
                //参数名
                edmMethodArg.setEdmaName(params.get(i));
                //参数数据类型
                edmMethodArg.setEdmaDataType(IsEqualUtil.dataType(String.valueOf(map.get(params.get(i)))));
                list.add(edmMethodArg);
            }
            //方法名
            ed.setEdmmName(mri.getMethodName());
            //程序类型
            ed.setEdmmProgramType(mri.getProgramCate().getnCode());
            //方法的所属类名
            ed.setEdmcName(mri.getEdmClass());
            //插入方法的算法描述
            ed.setEdmmArithmeticDesc(IsEqualUtil.methodFullUrl(mri));
            ed.setEdmmRequestType(ReqType.getReqTypeByReqMethod(mri.getMethodReqType()).getReqType());
            //插入方法的类型：一般方法、联动方法、卷积方法
            ed.setEdmmMethodType(mri.getMethodType().getType());
            //插入方法的执行类型：同步方法、异步方法、自动方法、定时方法
            ed.setEdmmExecuteType(mri.getMethodExecType().getType());
            //插入方法的执行频率
            ed.setEdmmExecRate(mri.getMethodExeFrequency().getRate());
            //方法的描述
            ed.setEdmmDesc(mri.getMethodDesc());
            //添加方法分类
            ed.setEdmmTypeName(mri.getMethodCate());
            //方法的状态：启用 or 未启用
            ed.setEdmmStatus(mri.getMethodStatus().getStatusCode());
            //同步和异步方法的超时时间
            ed.setTimeout(mri.getTimeout());
            logger.info("MethodRegisterInfo里面的数据为:" + mri);
            logger.info("EdmMethodVO里面的数据为：" + ed);
            //传入方法数据
            ev.setEdmMethod_in(ed);
            //传入参数对象
            ev.setEdmMethodInsertList_in(list);
            EdmMethodArg edmMethodArg = new EdmMethodArg();
            //传入方法的返回值名称
            edmMethodArg.setEdmaName(mri.getReturnType());
            //传入方法的返回值类型
            edmMethodArg.setEdmaDataType(IsEqualUtil.dataType(mri.getReturnType()));
            //传入方法的返回描述
            edmMethodArg.setEdmaDesc(mri.getReturnType());
            //传入返回数据
            ev.setEdmMethodreturn_in(edmMethodArg);
        } catch (Exception e) {
            logger.error("插入EDM方法出错 ：" + e.getMessage(), e);
        }
        return ev;
    }

    /**
     * @param edmServiceName
     * @param mri
     * @param edmMethodAndArgVO
     * @return com.huntkey.rx.sceo.method.register.plugin.entity.EdmMethodAndArgVO
     * @description 方法更新
     * @method updateEdm
     */
    public static EdmMethodAndArgVO updateEdm(String edmServiceName, MethodRegisterInfo mri, EdmMethodAndArgVO edmMethodAndArgVO) {

        EdmMethodVO vo = edmMethodAndArgVO.getEdmMethod_in();
        //将所属类id置空
        vo.setEdmmEdmcId(null);
        //将分类id置空
        vo.setEdmmType(null);
        List<EdmMethodArg> list = new ArrayList<EdmMethodArg>();
        Map<String, String> map = mri.getParamsNameAndTypeMap();
        //取出参数列表
        List<String> params = new ArrayList<String>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            params.add(entry.getKey());
        }
        for (int i = 0; i < map.size(); i++) {
            EdmMethodArg edmMethodArg = new EdmMethodArg();
            //参数名
            edmMethodArg.setEdmaName(params.get(i));
            //参数数据类型
            edmMethodArg.setEdmaDataType(IsEqualUtil.dataType(String.valueOf(map.get(params.get(i)))));
            list.add(edmMethodArg);
        }
        if (!StringUtil.isNullOrEmpty(mri.getProgramCate().getnCode())) {
            //更新传入的程序类型
            vo.setEdmmProgramType(mri.getProgramCate().getnCode());
        }

        if (!StringUtil.isNullOrEmpty(mri.getMethodType().getType())) {
            //更新方法的类型同步、异步
            vo.setEdmmMethodType(mri.getMethodType().getType());
        }

        if (!StringUtil.isNullOrEmpty(mri.getMethodExecType().getType())) {
            //更新方法的执行类型
            vo.setEdmmExecuteType(mri.getMethodExecType().getType());
        }

        if (!StringUtil.isNullOrEmpty(mri.getEdmClass())) {
            //更新方法的所属类
            vo.setEdmcName(mri.getEdmClass());
        }

        if (!StringUtil.isNullOrEmpty(IsEqualUtil.methodFullUrl(mri))) {
            //算法描述
            vo.setEdmmArithmeticDesc(IsEqualUtil.methodFullUrl(mri));
        }

        if (!StringUtil.isNullOrEmpty(ReqType.getReqTypeByReqMethod(mri.getMethodReqType()).getReqType())) {
            //更新方法的请求类型
            vo.setEdmmRequestType(ReqType.getReqTypeByReqMethod(mri.getMethodReqType()).getReqType());
        }

        if (!StringUtil.isNullOrEmpty(mri.getMethodExeFrequency().getRate())) {
            //更新执行频率
            vo.setEdmmExecRate(mri.getMethodExeFrequency().getRate());
        }

        if (!StringUtil.isNullOrEmpty(mri.getMethodDesc())) {
            //更新方法描述
            vo.setEdmmDesc(mri.getMethodDesc());
        }

        if (!StringUtil.isNullOrEmpty(mri.getMethodCate())) {
            //更新方法分类
            vo.setEdmmTypeName(mri.getMethodCate());
        }

        if (!StringUtil.isNullOrEmpty(mri.getMethodStatus().getStatusCode())) {
            //方法的状态：启用 or 未启用
            vo.setEdmmStatus(mri.getMethodStatus().getStatusCode());
        }
        if (!StringUtil.isNullOrEmpty(mri.getTimeout())) {
            //同步和异步方法的超时时间
            vo.setTimeout(mri.getTimeout());
        }
        logger.info("MethodRegisterInfo里面的数据为：" + mri);
        logger.info("EdmMethodVO里面的数据为" + vo);
        //更新方法的基本信息
        edmMethodAndArgVO.setEdmMethod_in(vo);
        //更新输入参数
        edmMethodAndArgVO.setEdmMethodInsertList_in(list);
        EdmMethodArg edmMethodArg = new EdmMethodArg();
        //传入方法的返回值名称
        edmMethodArg.setEdmaName(mri.getReturnType());
        //传入方法的返回值类型
        edmMethodArg.setEdmaDataType(IsEqualUtil.dataType(mri.getReturnType()));
        //传入方法的返回描述
        edmMethodArg.setEdmaDesc(mri.getReturnType());
        //更新返回值类型
        edmMethodAndArgVO.setEdmMethodreturn_in(edmMethodArg);
        return edmMethodAndArgVO;
    }
}

