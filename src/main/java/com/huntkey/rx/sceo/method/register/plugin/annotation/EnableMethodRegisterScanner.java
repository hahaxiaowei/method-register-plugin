package com.huntkey.rx.sceo.method.register.plugin.annotation;

import com.huntkey.rx.sceo.method.register.plugin.config.MethodRegisterScannerConfig;
import com.huntkey.rx.sceo.method.register.plugin.config.RestTemplateConfig;
import com.huntkey.rx.sceo.method.register.plugin.util.RestUtil;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Created by lulx on 2017/11/15 0015 下午 16:10
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@EnableRestTemplate()
@Import({MethodRegisterScannerConfig.class})
@Documented
public @interface EnableMethodRegisterScanner {
    public Class<?> startApplicationClass();

    public String edmServiceName() default "modeler-provider";

    public String serviceApplicationName();
}
