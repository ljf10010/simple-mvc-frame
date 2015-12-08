/*
 * Copyright (c) 2015. Enum.Lin all rights reserved.
 */

package com.enumlin.core.helper;

import com.enumlin.core.utils.ReflectionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/*
 * Bean 助手类
 *
 * @author   Enum.Lin
 * @version  1.0
 * @since    2015-12-02
 * 
 */
public class BeanHelper {
    private static final Map<Class<?>, Object> BEAN_MAP = new HashMap<>();

    static {
        Set<Class<?>> beanClass = ClassHelper.getBeanClass();

        for (Class<?> clz : beanClass) {
            BEAN_MAP.put(clz, ReflectionUtil.newInstance(clz));
        }
    }

    public static Map<Class<?>, Object> getBeanMap() {
        return BEAN_MAP;
    }

    public static <T> T getBean(Class<T> clz) {
        if (!BEAN_MAP.containsKey(clz)) {
            throw new RuntimeException("can not get bean by class: " + clz);
        }

        return (T) BEAN_MAP.get(clz);
    }


}
