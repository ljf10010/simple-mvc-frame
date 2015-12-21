/*
 * Copyright (c) 2015. Enum.Lin all rights reserved.
 */

package com.enumlin.core.helper;

import com.enumlin.core.bean.FormParam;
import com.enumlin.core.bean.Param;
import com.enumlin.core.utils.StreamUtil;
import com.enumlin.core.utils.StringUtil;
import org.apache.commons.lang3.ArrayUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;

/*
 * 请求辅助类
 *
 * @author   Enum.Lin
 * @version  1.0
 * @since    2015-12-21
 * 
 */
public class RequestHelper {
    /**
     * 创建请求对象
     *
     * @param request
     * @return
     * @throws IOException
     */
    public static Param createParam(HttpServletRequest request) throws IOException {
        List<FormParam> formParamList = new ArrayList<>();
        formParamList.addAll(parseParammeterNames(request));
        formParamList.addAll(parseInputStream(request));
        return new Param(formParamList);
    }

    private static Collection<? extends FormParam> parseInputStream(HttpServletRequest request) throws IOException {
        List<FormParam> formParamList = new ArrayList<>();
        String body = StreamUtil.getString(request.getInputStream());
        if (StringUtil.isNotEmpty(body)) {
            String[] kvs = StringUtil.splitString(body, "&");
            if (ArrayUtils.isNotEmpty(kvs)) {
                for (String kv : kvs) {
                    String[] arr = StringUtil.splitString(kv, "=");
                    if (ArrayUtils.isNotEmpty(arr) && arr.length == 2) {
                        formParamList.add(new FormParam(arr[0], arr[1]));
                    }
                }
            }
        }
        return formParamList;
    }

    private static Collection<? extends FormParam> parseParammeterNames(HttpServletRequest request) {
        List<FormParam> formParamList = new ArrayList<>();
        Enumeration<String> names = request.getParameterNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            String[] values = request.getParameterValues(name);
            if (ArrayUtils.isNotEmpty(values)) {
                int valueSize = values.length;
                Object value = null;
                if (valueSize == 1) {
                    value = values[0];
                } else {
                    StringBuilder builder = new StringBuilder();
                    for (int i = 0; i < values.length; i++) {
                        builder.append(values[i]);
                        if (i != (valueSize - 1))
                            builder.append(StringUtil.SEPARATOR);
                    }
                    value = builder.toString();
                }
                formParamList.add(new FormParam(name, value));
            }
        }
        return formParamList;
    }
}
