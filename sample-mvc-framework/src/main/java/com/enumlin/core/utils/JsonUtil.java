/*
 * Copyright (c) 2015. Enum.Lin all rights reserved.
 */

package com.enumlin.core.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/*
 * Json 处理工具
 *
 * @author   Enum.Lin
 * @version  1.0
 * @since    2015-12-03
 * 
 */
public final class JsonUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * 对象转换成字符串
     *
     * @param target
     * @param <T>
     * @return
     */
    public static <T> String Object2String(T target) {
        String result = null;

        try {
            result = OBJECT_MAPPER.writeValueAsString(target);
        } catch (JsonProcessingException e) {
            LOGGER.error("object convert to string failure.", e);
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 字符装对象
     *
     * @param json
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T String2Object(String json, Class<T> type) {
        T target = null;

        try {
            target = OBJECT_MAPPER.readValue(json, type);
        } catch (IOException e) {
            LOGGER.error("string convert to object failure.", e);
            throw new RuntimeException(e);
        }
        return target;
    }
}
