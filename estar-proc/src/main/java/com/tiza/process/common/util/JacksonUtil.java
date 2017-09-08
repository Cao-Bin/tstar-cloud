package com.tiza.process.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Description: JacksonUtil
 * Author: DIYILIU
 * Update: 2016-03-22 9:25
 */
public class JacksonUtil {

    private static ObjectMapper mapper = new ObjectMapper();

    public static String toJson(Object obj){

        String rs = null;

        try {
            rs = mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {

            e.printStackTrace();
        }
        return rs;
    }

    public static <T> T  toObject(String content, Class<T> clazz) throws IOException {

        return (T) mapper.readValue(content, clazz);
    }
}
