/*
 * Copyright (c) 2015. Enum.Lin all rights reserved.
 */

package com.enumlin.core.helper;

import java.util.Properties;

import static com.enumlin.core.utils.ConfigConstant.*;
import static com.enumlin.core.utils.ProPertiesUtil.getString;
import static com.enumlin.core.utils.ProPertiesUtil.load;

/*
 * 属性文件助手类
 *
 * @author   Enum.Lin
 * @version  1.0
 * @since    2015-12-01
 * 
 */
public final class ConfigHelper {
    private static final Properties CONFIG_PROPS = load(CONFIG_FILE);

    public static String getJdbcDriver() {
        return getString(CONFIG_PROPS, JDBC_DRIVER);
    }

    public static String getJdbcUrl() {
        return getString(CONFIG_PROPS, JDBC_URL);
    }

    public static String getJdbcUsername() {
        return getString(CONFIG_PROPS, JDBC_USERNAME);
    }

    public static String getJdbcPassword() {
        return getString(CONFIG_PROPS, JDBC_PASSWORD);
    }

    public static String getAppBasePackage() {
        return getString(CONFIG_PROPS, APP_BASE);
    }

    public static String getAppJspPath() {
        return getString(CONFIG_PROPS, APP_JSP_PATH, "/WEB-INF/views/");
    }

    public static String getAppAssetPath() {
        return getString(CONFIG_PROPS, APP_ASSET_PATH, "/asset/");
    }
}
