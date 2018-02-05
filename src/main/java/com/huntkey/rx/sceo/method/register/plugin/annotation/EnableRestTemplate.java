package com.huntkey.rx.sceo.method.register.plugin.annotation;

import com.huntkey.rx.sceo.method.register.plugin.config.RestTemplateConfig;
import com.huntkey.rx.sceo.method.register.plugin.util.RestUtil;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Created by lulx on 2017/11/15 0015 上午 11:20
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import({RestTemplateConfig.class, RestUtil.class})
@Documented
public @interface EnableRestTemplate {

}
