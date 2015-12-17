/*
 * Copyright (c) 2015. Enum.Lin all rights reserved.
 */

package com.enumlin.core.helper;

import com.enumlin.core.annotation.ExcludeField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/*
 * 实体工具类
 *
 * @author   Enum.Lin
 * @version  1.0
 * @since    2015-11-30
 * 
 */
public class EntityHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(EntityHelper.class);

    /**
     * 实体转换成 Map
     *
     * @param entity
     * @return
     */
    public static Map<String, Object> entity2Map(Object entity) {
        Map<String, Object> result = null;
        if (entity == null) {
            LOGGER.error("entity convert to map failure: entity not equal null.");
            return result;
        }

        Class<?> entityClass = entity.getClass();
        result = new HashMap<>();
        for (Field field : entityClass.getDeclaredFields()) {
            try {
                if (!field.isAnnotationPresent(ExcludeField.class)) {
                    field.setAccessible(true);
                    result.put(field.getName(), field.get(entity));
                }
            } catch (IllegalAccessException e) {
                LOGGER.error("get field value failure.", e);
                throw new RuntimeException(e);
            }
        }

        return result;
    }
}
