/*
 * Copyright (c) 2015. Enum.Lin all rights reserved.
 */

package com.enumlin.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

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

    public static void copyStream(InputStream inputStream, OutputStream outputStream) {
        int length = 0;
        byte[] temp = new byte[1024 * 1024];
        try {
            while ((length = inputStream.read(temp)) != -1) {
                outputStream.write(temp, 0, length);
            }
        } catch (IOException e) {
            LOGGER.error("copy stream failure.", e);
        } finally {
            try {
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                LOGGER.error("close stream failure.", e);
            }
        }
    }
}
