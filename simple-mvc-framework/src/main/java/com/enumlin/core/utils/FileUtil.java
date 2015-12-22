/*
 * Copyright (c) 2015. Enum.Lin all rights reserved.
 */

package com.enumlin.core.utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/*
 * 文件工具类
 *
 * @author   Enum.Lin
 * @version  1.0
 * @since    2015-12-20
 * 
 */
public final class FileUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 去除文件路径
     *
     * @param s
     * @return
     */
    public static String getRealFileName(String s) {
        return FilenameUtils.getName(s);
    }

    /**
     * 创建文件
     *
     * @param filePath
     * @return
     */
    public static File createFile(String filePath) {
        File file = new File(filePath);
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            try {
                FileUtils.forceMkdir(file);
                file.setWritable(true);
                file.setReadable(true);
                file.setExecutable(true);
            } catch (IOException e) {
                LOGGER.error("create file failure.", e);
            }
        }
        return file;
    }
}
