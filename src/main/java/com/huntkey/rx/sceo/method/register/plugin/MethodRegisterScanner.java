package com.huntkey.rx.sceo.method.register.plugin;

import com.huntkey.rx.sceo.method.register.plugin.annotation.MethodRegister;
import com.huntkey.rx.sceo.method.register.plugin.entity.*;
import com.huntkey.rx.sceo.method.register.plugin.util.*;
import com.netflix.discovery.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by chenfei on 2017/11/1 0001.
 * <p>
 * EDM方法注册扫描器
 */
public class MethodRegisterScanner {

    private static Logger logger = LoggerFactory.getLogger(MethodRegisterScanner.class);

    private static String edmServiceName;

    private static List<String> ignoreParamType;

    private static String EMD_METHOD_CHECK_ERROR_MSG = "该类名不存在";

    static {
        ignoreParamType = new ArrayList<String>();
        ignoreParamType.add("javax.servlet.http.HttpServletResponse");
        ignoreParamType.add("javax.servlet.http.HttpServletRequest");
    }

    public MethodRegisterScanner(Class<?> clazz, String serviceApplicationName, String edmServiceNameConfig) {

        logger.info("Method Register Scanner has been initiated.");
        logger.info("application class name: {}", clazz.getName());
        edmServiceName = edmServiceNameConfig;
        SpringBootApplication sba = clazz.getAnnotation(SpringBootApplication.class);

        logger.info("sba basePackages: {}", sba.scanBasePackages().length);

        String packageName = clazz.getPackage().getName();
        Set<Class<?>> classes = getClasses(packageName);
        for (Class<?> c : classes) {
            Controller controller = c.getAnnotation(Controller.class);
            RestController restController = c.getAnnotation(RestController.class);
            if (null == controller && null == restController) {
                continue;
            }

            // get root reqeust mapping.
            RequestMapping rm = c.getAnnotation(RequestMapping.class);
            String[] path = {};
            if (null != rm) {
                path = rm.value();
            }

            if (path.length > 1) {
                throw new RuntimeException("root path must not be an array.");
            }

            String rootPath = "";
            if (path != null && path.length == 1) {
                logger.info("root path: {}", path[0]);
                rootPath = path[0];
            }
            handleRegisterMethods(c.getDeclaredMethods(), rootPath, serviceApplicationName);
        }
    }

    /**
     * 处理注册方法
     *
     * @param methods
     * @param rootPath
     * @param serviceApplicationName
     */
    private static void handleRegisterMethods(Method[] methods, String rootPath, String serviceApplicationName) {
        for (Method method : methods) {
            MethodRegister methodRegister = method.getAnnotation(MethodRegister.class);
            if (null == methodRegister) {
                continue;
            } else {
                MethodRegisterInfo mri = wrapMethodRegitsterInfo(method, serviceApplicationName, rootPath, methodRegister);
                registerMethod2EDM(mri);
            }
        }
    }

    /**
     * @param mri
     * @return void
     * @description 注册方法到EDM
     * @method registerMethod2EDM
     */
    private static void registerMethod2EDM(MethodRegisterInfo mri) {

        logger.info("注册方法到EDM start! mri:" + mri.toString());
        try {
            if (StringUtils.isEmpty(mri.getEdmClass()) || StringUtils.isEmpty(mri.getMethodName())) {
                logger.error("类名或者方法名为空!");
                return;
            }
            //方法名称唯一性校验
            String isExistUrl = "http://" + edmServiceName + "/methods/checkEdmmName?edmmName=" + mri.getMethodName() + "&edmcName=" + mri.getEdmClass();
            logger.info("判断EDM方法唯一性 isExistUrl:" + isExistUrl);
            Result isExistResult = RestUtil.doGet(isExistUrl, "");
            logger.info("判断EDM方法唯一性结果 isExistUrl:" + isExistResult);
            if (isExistResult.getRetCode() == 1) {
                logger.info("方法不存在，插入新方法");
                insertEdm(edmServiceName, mri);
            } else if (!StringUtils.isEmpty(isExistResult.getErrMsg()) && isExistResult.getErrMsg().equals(EMD_METHOD_CHECK_ERROR_MSG)) {
                logger.error("该方法所属类名不存在,不做操作" + isExistResult);
                throw new RuntimeException("该方法所属类名不存在,不做操作" + isExistResult);
            } else {
                logger.info("方法存在，执行更新操作");
                String queryUrl = "http://" + edmServiceName + "/methods/edmMethod/" + mri.getEdmClass() + "/" + mri.getMethodName();
                logger.info("查询EDM方法 queryUrl:" + queryUrl);
                Result queryResult = RestUtil.doGet(queryUrl, "");
                logger.info("查询EDM方法结果:" + queryResult);
                if (!Result.RECODE_SUCCESS.equals(queryResult.getRetCode()) || queryResult.getData()==null) {
                    return;
                }
                EdmMethodAndArgVO edmMethodAndArgVO = JsonToBeanUtil.jsonToObject(queryResult.getData(), EdmMethodAndArgVO.class);
                logger.info("MethodRegisterInfo里面的数据为：" + mri);
                logger.info("EdmMethodAndArgVO里面的数据为：" + edmMethodAndArgVO);
                if (edmMethodAndArgVO.getEdmMethod_in()!=null) {
                    logger.info("方法的基本信息不能为空，注册失败");
                    return;
                }
                if (IsEqualUtil.isMethodEqual(mri, edmMethodAndArgVO)) {
                    logger.info("方法的属性相等,不做更新操作");
                    return;
                } else {
                    updateEdm(edmServiceName, mri, edmMethodAndArgVO);
                }
            }
        } catch (Exception e) {
            logger.error("注册方法到EDM出错 ：" + e.getMessage(), e);
        }
    }

    /**
     * @param edmServiceName
     * @param mri
     * @return void
     * @description 方法信息输入
     * @method insertEdm
     */
    private static void insertEdm(String edmServiceName, MethodRegisterInfo mri) {
        try {
            String addUrl = "http://" + edmServiceName + "/methods/";
            EdmMethodAndArgVO ev = ExecMethodUtil.insertEdm(edmServiceName, mri);
            logger.info("EdmMethodAndArgVO里面的数据为：" + ev);
            logger.info("插入EDM方法 addUrl：" + addUrl);
            Result addResult = RestUtil.doPost(addUrl, ev);
            logger.info("插入EDM方法结果：" + addResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param edmServiceName
     * @param mri
     * @return void
     * @description 更新方法信息
     * @method updateEdm
     */
    private static void updateEdm(String edmServiceName, MethodRegisterInfo mri, EdmMethodAndArgVO edmMethodAndArgVO) {

        //将传来的EdmMethodAndArgVO进行更新操作
        EdmMethodAndArgVO EMAndArgVo = ExecMethodUtil.updateEdm(edmServiceName, mri, edmMethodAndArgVO);
        String updateUrl = "http://" + edmServiceName + "/methods/";
        logger.info("更新EDM方法 updateUrl：" + updateUrl);
        Result updateResult = RestUtil.doPut(updateUrl, EMAndArgVo);
        logger.info("edmMethodAndArgVO" + "里面的数据为" + edmMethodAndArgVO);
        logger.info("更新EDM方法结果：" + updateResult);
    }

    /**
     * 封装方法注册信息
     *
     * @param method
     * @param serviceApplicationName
     * @param rootPath
     * @param methodRegister
     * @return
     */
    private static MethodRegisterInfo wrapMethodRegitsterInfo(Method method, String serviceApplicationName,
                                                              String rootPath, MethodRegister methodRegister) {

        MethodRegisterInfo mri = new MethodRegisterInfo();
        mri.setMethodName(method.getName());
        RequestMapping rm = method.getAnnotation(RequestMapping.class);
        String requestPath = rootPath;
        RequestMethod methodReqType = RequestMethod.GET;
        if (null != rm) {
            requestPath += rm.value().length != 0 ? rm.value()[0] : "";
            methodReqType = rm.method().length != 0 ? rm.method()[0] : RequestMethod.GET;
        }

        mri.setRequestPath(requestPath);
        mri.setMethodReqType(methodReqType);
        String edmClass = methodRegister.edmClass();
        MethodExeFrequency methodExeFrequency = methodRegister.methodExeFrequency();
        int methodExeInterval = methodRegister.methodExeInterval();
        MethodType methodType = methodRegister.methodType();
        MethodExecType methodExecType = methodRegister.methodExecType();
        ProgramCate programCate = methodRegister.programCate();
        MethodStatus methodStatus = methodRegister.methodStatus();
        String[] reqParamsNameNoPathVariable = methodRegister.getReqParamsNameNoPathVariable();
        String methodCate = methodRegister.methodCate();
        String methodDesc = methodRegister.methodDesc();
        Integer timeOut = methodRegister.timeOut();
        //方法所属类
        mri.setEdmClass(edmClass);
        //方法的执行频率
        mri.setMethodExeFrequency(methodExeFrequency);
        //方法的执行间隔
        mri.setMethodExeInterval(methodExeInterval);
        //方法的类型:一般方法、联动方法、卷积方法
        mri.setMethodType(methodType);
        //方法的执行类型：同步、异步、自动、定时
        mri.setMethodExecType(methodExecType);
        //方法的程序类型
        mri.setProgramCate(programCate);
        //方法的状态
        mri.setMethodStatus(methodStatus);
        //方法的参数描述
        mri.setGetReqParamsNameNoPathVariable(reqParamsNameNoPathVariable);
        mri.setServiceApplicationName(serviceApplicationName);
        //方法的分类
        mri.setMethodCate(methodCate);
        //方法的描述
        mri.setMethodDesc(methodDesc);
        //同步和异步方法的数据读取超时时间
        mri.setTimeout(timeOut);
        logger.info("edmClass: {}, methodExeFrequency: {}, methodExeInterval: {}, methodType: {}, methodExecType: {},timeOut: {},timeOps: {}",
                edmClass, methodExeFrequency, methodExeInterval, methodType, methodExecType, timeOut);

        // get args & return value.
        LocalVariableTableParameterNameDiscoverer u =
                new LocalVariableTableParameterNameDiscoverer();
        Class<?>[] parameterTypes = method.getParameterTypes();
        String[] params = u.getParameterNames(method);
        Map<String, String> paramsNameAndTypeMap = new HashMap<String, String>();
        for (int i = 0; i < params.length; i++) {
            logger.info("pType: {}, pName : {}", parameterTypes[i].getName(), params[i]);
            if (ignoreParamType.contains(parameterTypes[i].getName())) {
                continue;
            }
            paramsNameAndTypeMap.put(params[i], parameterTypes[i].getName());
        }
        mri.setParamsNameAndTypeMap(paramsNameAndTypeMap);
        Class<?> returnType = method.getReturnType();
        logger.info("returnType: {}", null != returnType ? returnType.getName() : null);
        mri.setReturnType(null != returnType ? returnType.getName() : null);

        return mri;
    }

    /**
     * 获取该包路径下所有的class
     *
     * @param pack
     * @return
     */
    public static Set<Class<?>> getClasses(String pack) {

        Set<Class<?>> classes = new LinkedHashSet<Class<?>>();

        try {
            boolean recursive = true;
            String packageName = pack;
            String packageDirName = packageName.replace('.', '/');
            Enumeration<URL> dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);

            while (dirs.hasMoreElements()) {
                URL url = dirs.nextElement();
                String protocol = url.getProtocol();
                if ("file".equals(protocol)) {
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    findAndAddClassesInPackageByFile(packageName, filePath, recursive, classes);

                } else if ("jar".equals(protocol)) {
                    JarFile jar = ((JarURLConnection) url.openConnection()).getJarFile();
                    Enumeration<JarEntry> entries = jar.entries();
                    while (entries.hasMoreElements()) {
                        JarEntry entry = entries.nextElement();
                        String name = entry.getName();
                        if (name.charAt(0) == '/') {
                            name = name.substring(1);
                        }
                        if (name.startsWith(packageDirName)) {
                            int idx = name.lastIndexOf('/');
                            if (idx != -1) {
                                packageName = name.substring(0, idx).replace('/', '.');
                            }
                            if ((idx != -1) || recursive) {
                                if (name.endsWith(".class") && !entry.isDirectory()) {
                                    String className = name.substring(packageName.length() + 1, name.length() - 6);
                                    classes.add(Class.forName(packageName + '.' + className));
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return classes;
    }

    /**
     * 以文件的形式来获取包下的所有Class
     *
     * @param packageName
     * @param packagePath
     * @param recursive
     * @param classes
     */
    public static void findAndAddClassesInPackageByFile(String packageName, String packagePath, final boolean recursive, Set<Class<?>> classes) {
        File dir = new File(packagePath);
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        File[] dirFiles = dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return (recursive && file.isDirectory()) || (file.getName().endsWith(".class"));
            }
        });
        for (File file : dirFiles) {
            if (file.isDirectory()) {
                findAndAddClassesInPackageByFile(packageName + "." + file.getName(), file.getAbsolutePath(), recursive, classes);
            } else {
                String className = file.getName().substring(0, file.getName().length() - 6);
                try {
                    classes.add(Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + className));
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}

