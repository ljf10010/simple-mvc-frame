/*
 * Copyright (c) 2015. Enum.Lin all rights reserved.
 */

package com.enumlin.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/*
 * 反射工具类
 *
 * @author   Enum.Lin
 * @version  1.0
 * @since    2015-12-01
 * 
 */
public class ReflectionUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReflectionUtil.class);

    public static Object newInstance(Class<?> clz) {
        Object instance;

        try {
            instance = clz.newInstance();
        } catch (Exception e) {
            LOGGER.error("new instance failure.", e);
            throw new RuntimeException(e);
        }

        return instance;
    }

    public static Object invokeMethod(Object obj, Method method, Object... params) {
        Object result;

        try {
            method.setAccessible(true);
            result = method.invoke(obj, params);
        } catch (Exception e) {
            LOGGER.error("invoke method failure.", e);
            throw new RuntimeException(e);
        }

        return result;
    }

    public static void setFieldValue(Object obj, Field field, Object params) {
        try {
            field.setAccessible(true);
            field.set(obj, params);
        } catch (IllegalAccessException e) {
            LOGGER.error("set field value failure.", e);
            throw new RuntimeException(e);
        }
    }
}
