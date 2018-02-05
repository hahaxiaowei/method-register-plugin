package com.huntkey.rx.sceo.method.register.plugin.config;

import com.codingapi.tx.springcloud.http.TransactionHttpRequestInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Created by lulx on 2017/11/15 0015 上午 11:57
 */
@Configuration
public class RestTemplateConfig {

    private static Logger logger = LoggerFactory.getLogger(RestTemplateConfig.class);

    @Autowired
    private RestTemplateBuilder builder;

    @Bean(name="remoteRestTemplate")
    @LoadBalanced
    @ConditionalOnMissingBean
    RestTemplate restTemplate(){
        logger.info("--------restTemplate init--------");
        return builder.interceptors(new TransactionHttpRequestInterceptor()).build();
    }

}
