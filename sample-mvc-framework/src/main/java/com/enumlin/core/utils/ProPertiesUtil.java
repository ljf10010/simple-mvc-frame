/*
 * Copyright (c) 2015. Enum.Lin all rights reserved.
 */

package com.enumlin.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/*
 * 属性工具类
 *
 * @author   Enum.Lin
 * @version  1.0
 * @since    2015-11-30
 * 
 */
public class ProPertiesUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProPertiesUtil.class);

    public static Properties load(String fileName) {
        Properties properties = null;
        InputStream in = null;

        try {
            in = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            if (in == null) {
                throw new FileNotFoundException(fileName + " file is not found.");
            }
            properties = new Properties();
            properties.load(in);
        } catch (IOException e) {
            LOGGER.error("load properties file failure.", e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    LOGGER.error("close input stream failure.", e);
                }
            }
        }
        return properties;
    }

    public static String getString(Properties properties, String key) {
        return properties.getProperty(key, "");
    }

    public static String getString(Properties properties, String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public static int getInt(Properties properties, String key) {
        return getInt(properties, key, 0);
    }

    public static int getInt(Properties properties, String key, int defaultValue) {
        int value = defaultValue;
        if (properties.getProperty(key) != null) {
            value = Integer.parseInt(properties.getProperty(key));
        }

        return value;
    }

    public static boolean getBoolean(Properties properties, String key) {
        return getBoolean(properties, key, false);
    }

    public static boolean getBoolean(Properties properties, String key, boolean defaultValue) {
        boolean value = defaultValue;
        if (properties.getProperty(key) != null) {
            value = Boolean.parseBoolean(properties.getProperty(key));
        }
        return value;
    }
}
