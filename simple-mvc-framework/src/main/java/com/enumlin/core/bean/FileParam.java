/*
 * Copyright (c) 2015. Enum.Lin all rights reserved.
 */

package com.enumlin.core.bean;

import java.io.InputStream;

/*
 * 文件参数
 *
 * @author   Enum.Lin
 * @version  1.0
 * @since    2015-12-19
 * 
 */
public class FileParam {
    private String fieldName;
    private String fileName;
    private long fileSize;
    private String contentType;
    private InputStream inputStream;

    public FileParam(String contentType, String fieldName, String fileName, long fileSize, InputStream inputStream) {
        this.contentType = contentType;
        this.fieldName = fieldName;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.inputStream = inputStream;
    }

    public String getContentType() {
        return contentType;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getFileName() {
        return fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public InputStream getInputStream() {
        return inputStream;
    }
}
