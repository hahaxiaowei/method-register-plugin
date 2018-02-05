package com.huntkey.rx.sceo.method.register.plugin.util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;


/**
 * Created by sunwei on 2017/11/24 Time:17:05
 */
public class JsonToBeanUtil<T> {

    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * @param object
     * @param pojoClass
     * @return T
     * @description 传入json格式字符串：泛型转换，将对象转换为对象
     * @method JsonToObject
     */
    public static <T> T jsonToObject(Object object, Class<T> pojoClass) throws JsonMappingException,
            JsonParseException, IOException {
        String jsonAsString = objectMapper.writeValueAsString(object);
        return objectMapper.readValue(jsonAsString, pojoClass);
    }
}
