/*
 * Copyright (c) 2015. Enum.Lin all rights reserved.
 */

package com.enumlin.core.helper;

import com.enumlin.core.bean.FileParam;
import com.enumlin.core.bean.FormParam;
import com.enumlin.core.bean.Param;
import com.enumlin.core.utils.CollectionUtil;
import com.enumlin.core.utils.FileUtil;
import com.enumlin.core.utils.StreamUtil;
import com.enumlin.core.utils.StringUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
 * 文件上传工具类
 *
 * @author   Enum.Lin
 * @version  1.0
 * @since    2015-12-20
 * 
 */
public class UploadHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(UploadHelper.class);

    private static ServletFileUpload servletFileUpload;

    /**
     * 初始化
     *
     * @param servletContext
     */
    public static void init(ServletContext servletContext) {
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");

        servletFileUpload = new ServletFileUpload(new DiskFileItemFactory(DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD, repository));
        int uploadLimit = ConfigHelper.getAppUploadLimit();

        if (uploadLimit != 0) {
            servletFileUpload.setFileSizeMax(uploadLimit * 1024 * 1024);
        }
    }

    /**
     * 检查请求是否为 multipart 类型
     *
     * @param request
     * @return
     */
    public static boolean isMultipart(HttpServletRequest request) {
        return ServletFileUpload.isMultipartContent(request);
    }

    /**
     * 创建请求参数
     *
     * @param request
     * @return
     */
    public static Param createParam(HttpServletRequest request) {
        List<FileParam> fileParamList = new ArrayList<>();
        List<FormParam> formParamList = new ArrayList<>();

        try {
            Map<String, List<FileItem>> fileItemListMap = servletFileUpload.parseParameterMap(request);
            if (CollectionUtil.isNotEmpty(fileItemListMap)) {
                for (Map.Entry<String, List<FileItem>> fielListEntry : fileItemListMap.entrySet()) {
                    String fieldName = fielListEntry.getKey();
                    List<FileItem> fileItemList = fielListEntry.getValue();
                    if (CollectionUtil.isNotEmpty(fileItemList)) {
                        for (FileItem fileItem : fileItemList) {
                            if (fileItem.isFormField()) {
                                String value = fileItem.getString("UTF-8");
                                formParamList.add(new FormParam(fieldName, value));
                            } else {
                                String fileName = FileUtil.getRealFileName(new String(fileItem.getName().getBytes(), "UTF-8"));
                                if (StringUtil.isNotEmpty(fileName)) {
                                    long fileSize = fileItem.getSize();
                                    String contentType = fileItem.getContentType();
                                    InputStream inputStream = fileItem.getInputStream();
                                    fileParamList.add(new FileParam(contentType, fieldName, fileName, fileSize, inputStream));
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("create param failure", e);
        }

        return new Param(fileParamList, formParamList);
    }

    /**
     * 上传文件
     *
     * @param basePath
     * @param fileParam
     */
    public static void uploadFile(String basePath, FileParam fileParam) {
        try {
            if (fileParam != null) {
                String filePath = basePath + fileParam.getFileName();
                FileUtil.createFile(basePath);
                InputStream inputStream = new BufferedInputStream(fileParam.getInputStream());
                OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(filePath));
                StreamUtil.copyStream(inputStream, outputStream);
            }

        } catch (Exception e) {
            LOGGER.error("upload file failure.", e);
        }
    }

    /**
     * 批量上传文件
     *
     * @param basePath
     * @param fileParamList
     */
    public static void batchUploadFile(String basePath, List<FileParam> fileParamList) {
        if (CollectionUtil.isNotEmpty(fileParamList)) {
            for (FileParam fileParam : fileParamList) {
                uploadFile(basePath, fileParam);
            }
        }
    }
}
