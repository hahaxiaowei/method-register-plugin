package com.huntkey.rx.sceo.method.register.plugin.util;

import com.huntkey.rx.commons.utils.rest.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lulx on 2017/10/14 0014 上午 10:37
 */
@Service
@Lazy(false)
@DependsOn("remoteRestTemplate")
public class RestUtil {
    private static Logger logger = LoggerFactory.getLogger(RestUtil.class);

    @Autowired
    @Qualifier(value = "remoteRestTemplate")
    RestTemplate restTemplate;

    private static RestTemplate templateHolder;

    @PostConstruct
    public void init() {
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectTimeout(5000);
        requestFactory.setReadTimeout(20000);
        requestFactory.setConnectionRequestTimeout(50000);
        restTemplate.setRequestFactory(requestFactory);
        templateHolder = restTemplate;
    }

    /**
     * Send Get request
     *
     * @param url    like http://HELLO-SERVICE/hello, HELLO-SERVICE can be discovered in Eureka.
     *               http://driver-service-demo/index/get?params={params}
     * @param params
     * @return
     */
    public static Result doGet(String url, String params) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("params", params);

        long start = System.currentTimeMillis();
        ResponseEntity<Result> responseEntity = templateHolder.getForEntity(url, Result.class, map);
        long end = System.currentTimeMillis();
        logger.info("[-doGet-] Spend time : {}", (end - start));

        return responseEntity.getBody();
    }

    public static Result doGet(String url, Map<String, Object> map) throws MalformedURLException {

        long start = System.currentTimeMillis();
        ResponseEntity<Result> responseEntity = templateHolder.getForEntity(url, Result.class, map);
        long end = System.currentTimeMillis();
        logger.info("[-doGet-] Spend time : {}", (end - start));

        return responseEntity.getBody();
    }

    /**
     * Send Post request
     *
     * @param url like http://HELLO-SERVICE/hello, HELLO-SERVICE can be discovered in Eureka.
     * @param obj
     * @return
     */
    public static Result doPost(String url, Object obj) {

        long start = System.currentTimeMillis();
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);

        HttpEntity formEntity = new HttpEntity<Object>(obj, headers);

        ResponseEntity<Result> responseEntity = templateHolder.postForEntity(url, formEntity, Result.class);

        long end = System.currentTimeMillis();
        logger.info("[-doPost-] Spend time : {}", (end - start));

        return responseEntity.getBody();
    }

    public static Result doPut(String url, Object params) {
        long start = System.currentTimeMillis();

        Result result = exchange(url, HttpMethod.PUT, Result.class, params);

        long end = System.currentTimeMillis();
        logger.info("[-doPut-] Spend time : {}", (end - start));
        return result;
    }

    public static Result doPatch(String url, Object params) {
        long start = System.currentTimeMillis();

        Result result = exchange(url, HttpMethod.PATCH, Result.class, params);

        long end = System.currentTimeMillis();
        logger.info("[-doPatch-] Spend time : {}", (end - start));
        return result;
    }

    public static Result doDelete(String url, String params) {
        long start = System.currentTimeMillis();

        Result result = exchange(url, HttpMethod.DELETE, Result.class, params);

        long end = System.currentTimeMillis();
        logger.info("[-doDelete-] Spend time : {}", (end - start));
        return result;
    }

    /**
     * 发送/获取 服务端数据(主要用于解决发送put,delete方法无返回值问题).
     *
     * @param url      绝对地址
     * @param method   请求方式
     * @param bodyType 返回类型
     * @param <T>      返回类型
     * @return 返回结果(响应体)
     */
    public static <T> T exchange(String url, HttpMethod method, Class<T> bodyType, Object params) {
        // 请求头
        HttpHeaders headers = new HttpHeaders();
        MimeType mimeType = MimeTypeUtils.parseMimeType("application/json");
        MediaType mediaType = new MediaType(mimeType.getType(), mimeType.getSubtype(), Charset.forName("UTF-8"));
        // 请求体
        headers.setContentType(mediaType);
        // 发送请求
        HttpEntity<Object> entity = new HttpEntity<Object>(params, headers);
        ResponseEntity<T> resultEntity = templateHolder.exchange(url, method, entity, bodyType);
        return resultEntity.getBody();
    }
}
