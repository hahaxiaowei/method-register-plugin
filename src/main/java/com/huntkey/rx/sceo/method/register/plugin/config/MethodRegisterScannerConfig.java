package com.huntkey.rx.sceo.method.register.plugin.config;

import com.huntkey.rx.sceo.method.register.plugin.annotation.EnableMethodRegisterScanner;
import com.huntkey.rx.sceo.method.register.plugin.util.MethodRegisterScannerStartBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

/**
 * Created by lulx on 2017/11/15 0015 下午 13:53
 */
@Configuration
public class MethodRegisterScannerConfig implements ImportBeanDefinitionRegistrar {

    private static Logger logger = LoggerFactory.getLogger(MethodRegisterScannerConfig.class);

    private Class<?> startApplicationClass;
    private String serviceApplicationName;
    private String edmServiceName;
    protected String BEAN_NAME = "methodRegisterScannerStartBean";

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry) {
        Map<String, Object> attrs = annotationMetadata.getAnnotationAttributes(EnableMethodRegisterScanner.class.getName());
        Class<?>  startApplicationClass = (Class<?>) attrs.get("startApplicationClass");
        String edmServiceName = (String) attrs.get("edmServiceName");
        String serviceApplicationName = (String) attrs.get("serviceApplicationName");
        logger.info("startApplicationClass : " + startApplicationClass +
                        "edmServiceName : " + edmServiceName +
                        "serviceApplicationName : " + serviceApplicationName);
        this.startApplicationClass = startApplicationClass;
        this.serviceApplicationName = serviceApplicationName;
        this.edmServiceName = edmServiceName;
        if (!registry.containsBeanDefinition(BEAN_NAME)) {
            logger.info("------registry methodRegisterScannerStartBean-------");
            GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
            beanDefinition.setBeanClass(MethodRegisterScannerStartBean.class);
            MutablePropertyValues mutablePropertyValues = new MutablePropertyValues();
            mutablePropertyValues.addPropertyValue("startApplicationClass", startApplicationClass);
            mutablePropertyValues.addPropertyValue("serviceApplicationName", serviceApplicationName);
            mutablePropertyValues.addPropertyValue("edmServiceName", edmServiceName);
            beanDefinition.setPropertyValues(mutablePropertyValues);
            beanDefinition.setSynthetic(Boolean.TRUE);
            beanDefinition.setLazyInit(Boolean.FALSE);
            registry.registerBeanDefinition(BEAN_NAME, beanDefinition);
        }
    }
}
