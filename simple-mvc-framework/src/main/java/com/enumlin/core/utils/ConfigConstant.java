/*
 * Copyright (c) 2015. Enum.Lin all rights reserved.
 */

package com.enumlin.core.utils;

/*
 * 提供相关配置常量
 *
 * @author   Enum.Lin
 * @version  1.0
 * @since    2015-12-01
 * 
 */
public interface ConfigConstant {
    String CONFIG_FILE = "smart.properties";

    // 数据库
    String JDBC_DRIVER = "smart.framework.jdbc.driver";
    String JDBC_URL = "smart.framework.jdbc.url";
    String JDBC_USERNAME = "smart.framework.jdbc.username";
    String JDBC_PASSWORD = "smart.framework.jdbc.password";

    String APP_BASE = "smart.framework.app.base_package";
    String APP_JSP_PATH = "smart.framework.app.jsp_path";
    String APP_ASSET_PATH = "smart.framework.app.asset_path";

}
