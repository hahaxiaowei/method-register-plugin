package com.huntkey.rx.sceo.method.register.plugin.annotation;

import com.huntkey.rx.sceo.method.register.plugin.util.ExecUtil;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Created by lulx on 2017/11/16 0016 下午 17:19
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@EnableRestTemplate()
@Import({ExecUtil.class})
@Documented
public @interface EnableDriverMethod {
}
