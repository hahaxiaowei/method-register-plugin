package com.huntkey.rx.sceo.method.register.plugin.util;

import com.huntkey.rx.sceo.method.register.plugin.MethodRegisterScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;

/**
 * Created by lulx on 2017/11/15 0015 下午 15:58
 */
public class MethodRegisterScannerStartBean implements CommandLineRunner {

    private static Logger logger = LoggerFactory.getLogger(MethodRegisterScannerStartBean.class);

    private Class<?> startApplicationClass;
    private String serviceApplicationName;
    private String edmServiceName;

    public Class<?> getStartApplicationClass() {
        return startApplicationClass;
    }

    public void setStartApplicationClass(Class<?> startApplicationClass) {
        this.startApplicationClass = startApplicationClass;
    }

    public String getServiceApplicationName() {
        return serviceApplicationName;
    }

    public void setServiceApplicationName(String serviceApplicationName) {
        this.serviceApplicationName = serviceApplicationName;
    }

    public String getEdmServiceName() {
        return edmServiceName;
    }

    public void setEdmServiceName(String edmServiceName) {
        this.edmServiceName = edmServiceName;
    }

    public MethodRegisterScannerStartBean() {
    }

    public MethodRegisterScannerStartBean(Class<?> startApplicationClass, String serviceApplicationName, String edmServiceName) {
        this.startApplicationClass = startApplicationClass;
        this.serviceApplicationName = serviceApplicationName;
        this.edmServiceName = edmServiceName;
    }

    @Override
    public void run(String... strings) throws Exception {
        logger.info("----MethodRegisterScanner run----");
        new MethodRegisterScanner(startApplicationClass, serviceApplicationName, edmServiceName);
    }
}
