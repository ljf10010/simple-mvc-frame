/*
 * Copyright (c) 2015. Enum.Lin all rights reserved.
 */

package com.enumlin.core.utils;

/*
 * 描述
 *
 * @author   Enum.Lin
 * @version  1.0
 * @since    2015-12-02
 * 
 */
public class CastUtil {
    public static Long castLong(Object o) {
        Long value = null;
        if (o != null) {
            value = Long.parseLong(o.toString());
        }
        return value;
    }

    public static String castString(Object o) {
        return String.valueOf(o);
    }

    /**
     * @param o
     * @return
     */
    public static double castDouble(Object o) {
        return Double.valueOf(o.toString());
    }

    public static int castInt(Object o) {
        return Integer.valueOf(o.toString());
    }

    public static boolean castBoolean(Object o) {
        return Boolean.valueOf(o.toString());
    }
}
