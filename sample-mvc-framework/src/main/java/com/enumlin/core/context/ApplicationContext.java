/*
 * Copyright (c) 2015. Enum.Lin all rights reserved.
 */

package com.enumlin.core.context;

import com.enumlin.core.helper.BeanHelper;
import com.enumlin.core.helper.ClassHelper;
import com.enumlin.core.helper.ControllerHelper;
import com.enumlin.core.helper.IocHelper;
import com.enumlin.core.utils.ClassUtil;

/*
 * 上下文类
 *
 * @author   Enum.Lin
 * @version  1.0
 * @since    2015-12-02
 * 
 */
public final class ApplicationContext {
    public static void init() {
        Class<?>[] classArray = {
                ClassHelper.class,
                BeanHelper.class,
                IocHelper.class,
                ControllerHelper.class
        };

        for (Class<?> clz : classArray) {
            ClassUtil.loadClass(clz.getName(), true);
        }
    }
}
