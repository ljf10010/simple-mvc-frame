/*
 * Copyright (c) 2015. Enum.Lin all rights reserved.
 */

package com.enumlin.core.helper;

import com.enumlin.core.annotation.Action;
import com.enumlin.core.bean.Handler;
import com.enumlin.core.bean.Request;
import com.enumlin.core.utils.CollectionUtil;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/*
 * 控制器助手类
 *
 * @author   Enum.Lin
 * @version  1.0
 * @since    2015-12-02
 * 
 */
public final class ControllerHelper {
    private static final Map<Request, Handler> ACTION_MAP = new HashMap<>();

    // 初始化请求映射
    static {
        Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();

        if (CollectionUtil.isNotEmpty(controllerClassSet)) {
            for (Class<?> controllerClass : controllerClassSet) {
                Method[] methods = controllerClass.getDeclaredMethods();

                if (ArrayUtils.isEmpty(methods)) continue;
                for (Method method : methods) {
                    if (!method.isAnnotationPresent(Action.class)) continue;

                    Action action = method.getAnnotation(Action.class);
                    String mapping = action.value();

                    if (!mapping.matches("\\w+:/\\w*")) continue;
                    String[] params = mapping.split(":");
                    if (ArrayUtils.isEmpty(params) || params.length != 2) continue;
                    Request request = new Request(params[0], params[1]);
                    Handler handler = new Handler(controllerClass, method);

                    ACTION_MAP.put(request, handler);
                }
            }
        }
    }

    /**
     * 获取 Handler
     *
     * @param requestMethod
     * @param requestPath
     * @return
     */
    public static Handler getHandler(String requestMethod, String requestPath) {
        Request request = new Request(requestMethod, requestPath);
        return ACTION_MAP.get(request);
    }
}
