/*
 * Copyright (c) 2015. Enum.Lin all rights reserved.
 */

package com.enumlin.core.utils;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/*
 * 类操作工具类
 *
 * @author   Enum.Lin
 * @version  1.0
 * @since    2015-12-01
 * 
 */
public class ClassUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClassUtil.class);

    /**
     * 获取类加载器
     *
     * @return
     */
    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * 加载类
     *
     * @param className
     * @param isInitailzed
     * @return
     */
    public static Class<?> loadClass(String className, boolean isInitailzed) {
        Class<?> clz = null;

        try {
            clz = Class.forName(className, isInitailzed, getClassLoader());
        } catch (ClassNotFoundException e) {
            LOGGER.error("load class failure.", e);
            throw new RuntimeException(e);
        }
        return clz;
    }

    /**
     * 加载包中的类
     *
     * @param packageName
     * @return
     */
    public static Set<Class<?>> getClassSet(String packageName) {
        Set<Class<?>> classSet = new HashSet<>();

        try {
            Enumeration<URL> urls = getClassLoader().getResources(packageName.replace(".", "/"));
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                if (url == null) continue;

                String protocol = url.getProtocol();
                if ("file".equals(protocol)) {
                    String packagePath = url.getPath().replaceAll("%20", " ");
                    addClass(classSet, packagePath, packageName);
                } else if ("jar".equals(protocol)) {
                    JarURLConnection connection = (JarURLConnection) url.openConnection();
                    if (connection == null) continue;

                    JarFile jarFile = connection.getJarFile();
                    Enumeration<JarEntry> entries = jarFile.entries();

                    while (entries.hasMoreElements()) {
                        JarEntry jarEntry = entries.nextElement();
                        String jarEntryName = jarEntry.getName();
                        if (jarEntryName.endsWith(".class")) {
                            String className = jarEntryName.substring(0, jarEntryName.lastIndexOf("."));
                            doAddClass(classSet, className);
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("get clas set failure.", e);
            throw new RuntimeException(e);
        }
        return classSet;
    }

    private static void addClass(Set<Class<?>> classSet, String packagePath, final String packageName) {
        File[] files = new File(packagePath).listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return (pathname.isFile() && pathname.getName().endsWith(".class")) || pathname.isDirectory();
            }
        });

        if (ArrayUtils.isNotEmpty(files)) {
            for (File file : files) {
                String fileName = file.getName();

                if (file.isFile()) {
                    String className = fileName.substring(0, fileName.lastIndexOf("."));
                    if (StringUtil.isNotEmpty(className)) {
                        className = packageName + "." + className;
                    }

                    doAddClass(classSet, className);
                } else {
                    String subPackagePath = fileName;
                    if (StringUtil.isNotEmpty(subPackagePath)) {
                        subPackagePath = packagePath + subPackagePath + "/";
                    }

                    String subPackageName = fileName;
                    if (StringUtil.isNotEmpty(subPackageName)) {
                        subPackageName = packageName + "." + subPackageName;
                    }

                    addClass(classSet, subPackagePath, subPackageName);
                }
            }
        }

    }

    private static void doAddClass(Set<Class<?>> classSet, String className) {
        classSet.add(loadClass(className, false));
    }
}
