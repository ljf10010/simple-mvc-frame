/*
 * Copyright (c) 2015. Enum.Lin all rights reserved.
 */

package com.enumlin.core.utils;

import org.apache.commons.lang3.StringUtils;

/*
 * 字符串工具
 *
 * @author   Enum.Lin
 * @version  1.0
 * @since    2015-11-30
 * 
 */
public class StringUtil {
    public static boolean isEmpty(String str) {
        if (str != null) {
            str = str.trim();
        }

        return StringUtils.isEmpty(str);
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }


    public static String[] spliString(String body, String separatorChars) {
        return StringUtils.split(body, separatorChars);
    }
}
