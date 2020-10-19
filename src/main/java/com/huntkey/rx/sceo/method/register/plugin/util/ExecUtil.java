package com.huntkey.rx.sceo.method.register.plugin.util;

import com.huntkey.rx.sceo.method.register.plugin.entity.ParamsVo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.nio.charset.Charset;

/**
 * Created by chenfei on 2017/10/21.
 */
@Component
@Lazy(false)
@DependsOn("remoteRestTemplate")
public final class ExecUtil {

    private static Logger logger = LoggerFactory.getLogger(ExecUtil.class);

    @Autowired
    @Qualifier(value = "remoteRestTemplate")
    RestTemplate restTemplate;

    private static RestTemplate templateHolder;


    private static String biz_driver_url = "http://biz-driver-method/methodExec/exec";

    @PostConstruct
    public void init() {
        logger.info("ExecUtil init start");
        logger.info("restTemplate {} : " + restTemplate);
        templateHolder = restTemplate;
        logger.info("templateHolder {} : " + templateHolder);
        logger.info("ExecUtil init end");
    }

    public static Result exec(ParamsVo params) {
        logger.info(" params {} :" + params.toString());
        // 请求头
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", params.getAuthorization());
        MimeType mimeType = MimeTypeUtils.parseMimeType("application/json");
        MediaType mediaType = new MediaType(mimeType.getType(), mimeType.getSubtype(), Charset.forName("UTF-8"));
        // 请求体
        headers.setContentType(mediaType);
        // 发送请求
        HttpEntity<Object> entity = new HttpEntity<Object>(params, headers);
        if (!StringUtils.isEmpty(params.getBizDriverIP())) {
            biz_driver_url = "http://" + params.getBizDriverIP() + "/methodExec/exec";
            RestTemplate rm = new RestTemplate();
            ResponseEntity<Result> responseEntity = rm.exchange(biz_driver_url, HttpMethod.POST, entity, Result.class);
            return responseEntity.getBody();
        } else {
            ResponseEntity<Result> responseEntity = templateHolder.exchange(biz_driver_url, HttpMethod.POST, entity, Result.class);
            logger.info("response StatusCode {} ：" + responseEntity.getStatusCodeValue());
            return responseEntity.getBody();
        }

    }
}
