/*
 * Copyright (c) 2015. Enum.Lin all rights reserved.
 */

package com.enumlin.core.helper;

import com.enumlin.core.annotation.Controller;
import com.enumlin.core.annotation.Service;
import com.enumlin.core.utils.ClassUtil;

import java.util.HashSet;
import java.util.Set;

/*
 * 类助手类
 *
 * @author   Enum.Lin
 * @version  1.0
 * @since    2015-12-01
 * 
 */
public class ClassHelper {
    private static final Set<Class<?>> CLASS_SET;

    static {
        String basePackagePath = ConfigHelper.getAppBasePackage();
        CLASS_SET = ClassUtil.getClassSet(basePackagePath);
    }

    public static Set<Class<?>> getClassSet() {
        return CLASS_SET;
    }

    /**
     * 获取应用包下的所有 Controller 类
     *
     * @return
     * @see Controller
     */
    public static Set<Class<?>> getServiceClassSet() {
        Set<Class<?>> serviceclass = new HashSet<>();

        for (Class<?> clz : getClassSet()) {
            if (clz.isAnnotationPresent(Service.class))
                serviceclass.add(clz);
        }

        return serviceclass;
    }

    /**
     * 获取应用包下的所有 Service 类
     *
     * @return
     * @see Service
     */
    public static Set<Class<?>> getControllerClassSet() {
        Set<Class<?>> controllerClass = new HashSet<>();

        for (Class<?> clz : getClassSet()) {
            if (clz.isAnnotationPresent(Controller.class))
                controllerClass.add(clz);
        }

        return controllerClass;
    }

    /**
     * 获取应用包下的所有 Bean 类(包括 Service 和 Controller)
     *
     * @return
     * @see Service
     * @see Controller
     */
    public static Set<Class<?>> getBeanClass() {
        Set<Class<?>> beanClass = new HashSet<>();
        beanClass.addAll(getServiceClassSet());
        beanClass.addAll(getControllerClassSet());

        return beanClass;
    }
}
