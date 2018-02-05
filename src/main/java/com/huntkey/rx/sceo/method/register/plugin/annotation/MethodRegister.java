package com.huntkey.rx.sceo.method.register.plugin.annotation;


import com.huntkey.rx.sceo.method.register.plugin.entity.*;

import java.lang.annotation.*;

/**
 * Created by chenfei on 2017/11/1 0001.
 *
 * 方法注册注解
 * 此注解主要是为了自动完成EDM中方法的注册，注解中描述EDM类、方法类型、方法执行频率、方法执行间隔
 * 以及定时执行的选项描述
 *
 * 根据此注解中的内容，程序启动时，自动完成方法的注册
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MethodRegister {

    /**
     * 方法描述
     *
     * @return
     */
    public String methodDesc() default "";

    /**
     * edm类名称，在edm中是唯一标识
     *
     * @return
     */
    public String edmClass();

    /**
     * 方法分类
     *
     * @return
     */
    public String methodCate();

    /**
     * 程序类型
     *
     * @return
     */
    public ProgramCate programCate()  default ProgramCate.Java;

    /**
     * 方法状态
     *
     * @return
     */
    public MethodStatus methodStatus() default MethodStatus.Enable;

    /**
     * 方法类别，默认一般方法
     *
     * @return
     */
    public MethodType methodType() default MethodType.GeneralMethod;

    /**
     * 方法执行类别，默认同步方法
     *
     * @return
     */
    public MethodExecType methodExecType() default MethodExecType.SyncMethod;

    /**
     * 自动方法执行频次
     * @return
     */
    public MethodExeFrequency methodExeFrequency() default MethodExeFrequency.Once;

    /**
     * 自动方法执行间隔，只对MethodExeFrequency.LOOP有效
     * 默认值：5分钟
     *
     * @return
     */
    public int methodExeInterval() default 5;

    /**
     *  get请求方法中非pathVariable 参数名
     */
    public String[] getReqParamsNameNoPathVariable() default {};

    /**
     * 同步和异步方法的数据读取超时时间
     */
    public int timeOut() default 20;

}
