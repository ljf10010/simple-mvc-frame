/*
 * Copyright (c) 2015. Enum.Lin all rights reserved.
 */

package com.enumlin.core.helper;

import com.enumlin.core.annotation.Inject;
import com.enumlin.core.annotation.Service;
import com.enumlin.core.utils.CollectionUtil;
import com.enumlin.core.utils.ReflectionUtil;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Field;
import java.util.Map;

/*
 * 依赖注入助手类
 *
 * @author   Enum.Lin
 * @version  1.0
 * @since    2015-12-02
 * 
 */
public final class IocHelper {
    static {
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        if (CollectionUtil.isNotEmpty(beanMap)) {
            for (Map.Entry<Class<?>, Object> entry : beanMap.entrySet()) {
                Class<?> beanClass = entry.getKey();
                Object beanInstance = entry.getValue();

                Field[] fields = beanClass.getDeclaredFields();
                if (ArrayUtils.isEmpty(fields)) continue;

                for (Field field : fields) {
                    if (field.isAnnotationPresent(Inject.class)) {
                        Class<?> beanFieldClass = field.getType();
                        Object beanFieldInstance = beanMap.get(beanFieldClass);

                        if (beanFieldInstance == null) continue;
                        ReflectionUtil.setFieldValue(beanInstance, field, beanFieldInstance);
                    }
                }
            }
        }
    }
}
