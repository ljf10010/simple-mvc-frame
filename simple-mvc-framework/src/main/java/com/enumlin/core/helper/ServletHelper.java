/*
 * Copyright (c) 2015. Enum.Lin all rights reserved.
 */

package com.enumlin.core.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/*
 * Servlet 辅助类
 *
 * @author   Enum.Lin
 * @version  1.0
 * @since    2015-12-22
 * 
 */
public class ServletHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServletHelper.class);
    private static final ThreadLocal<ServletHelper> SERVLET_HELPER = new ThreadLocal<>();

    private HttpServletRequest request;
    private HttpServletResponse response;

    private ServletHelper(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    /**
     * 初始化辅助类，每个请求(线程拥有独立的request和response)
     *
     * @param request
     * @param response
     */
    public static void init(HttpServletRequest request, HttpServletResponse response) {
        SERVLET_HELPER.set(new ServletHelper(request, response));
    }

    /**
     * 销毁辅助类
     */
    public static void destroy() {
        SERVLET_HELPER.remove();
    }

    /**
     * 获取 request
     *
     * @return
     */
    public static HttpServletRequest getRequest() {
        return SERVLET_HELPER.get().request;
    }

    /**
     * 获取 response
     *
     * @return
     */
    public static HttpServletResponse getResponse() {
        return SERVLET_HELPER.get().response;
    }

    /**
     * 获取 session
     *
     * @return
     */
    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    /**
     * 获取 ServletContext
     *
     * @return
     */
    public static ServletContext getContext() {
        return getRequest().getServletContext();
    }

    public static void setRequestAttribute(String key, Object value) {
        getRequest().setAttribute(key, value);
    }

    public static <T> T getRequestAttribute(String key) {
        return (T) getRequest().getAttribute(key);
    }

    public static void removeRequestAttribute(String key) {
        getRequest().removeAttribute(key);
    }

    /**
     * 重定向
     *
     * @param location
     */
    public static void sendRedirect(String location) {
        try {
            getResponse().sendRedirect(getRequest().getContextPath() + location);
        } catch (IOException e) {
            LOGGER.error("redirect failure", e);
        }
    }

    public static void setSessionAttribute(String key, Object value) {
        getSession().setAttribute(key, value);
    }

    public static <T> T getSessionAttribute(String key) {
        return (T) getSession().getAttribute(key);
    }

    public static void removeAttribute(String key) {
        getSession().removeAttribute(key);
    }

    /**
     * 使 session 失效
     */
    public static void invalidateSession() {
        getSession().invalidate();
    }
}
