/*
 * Copyright (c) 2015. Enum.Lin all rights reserved.
 */

package com.enumlin.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/*
 * 流操作控制器
 *
 * @author   Enum.Lin
 * @version  1.0
 * @since    2015-12-02
 * 
 */
public final class StreamUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(StreamUtil.class);

    public static String getString(InputStream in) {
        StringBuilder sb = new StringBuilder();

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        try {
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            LOGGER.error("get string failure.", e);
            throw new RuntimeException(e);
        }
        return sb.toString();
    }
}
