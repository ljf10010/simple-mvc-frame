/*
 * Copyright (c) 2015. Enum.Lin all rights reserved.
 */

package com.enumlin.core;

import com.enumlin.core.bean.Data;
import com.enumlin.core.bean.Handler;
import com.enumlin.core.bean.Param;
import com.enumlin.core.bean.View;
import com.enumlin.core.context.ApplicationContext;
import com.enumlin.core.helper.*;
import com.enumlin.core.utils.JsonUtil;
import com.enumlin.core.utils.ReflectionUtil;
import com.enumlin.core.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Map;

/*
 * 请求转发器
 *
 * @author   Enum.Lin
 * @version  1.0
 * @since    2015-12-02
 */
@WebServlet( urlPatterns = "/*", loadOnStartup = 0 ) // 拦截所有的请求
public class DispatcherServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(DispatcherServlet.class);

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        ApplicationContext.init();
        LOGGER.info("context is initailized.");

        // 获取 ServletContext 对象 (用于注册 Servlet)
        ServletContext context = servletConfig.getServletContext();

        // 注册处理 JSP 的 Servlet
        ServletRegistration jspServlet = context.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");

        // 注册处理静态资源的默认 Servlet
        ServletRegistration defaultServlet = context.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAppAssetPath() + "*");

        // 初始化文件上传组件
        UploadHelper.init(context);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestMethod = req.getMethod().toLowerCase();
        String requestPath = req.getPathInfo();

        // 忽略logo请求
        if (requestPath.equals("/favicon.ico"))
            return;

        Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);
        if (handler != null) {
            Class<?> controllerClass = handler.getControllerClass();
            Object controllerInstance = BeanHelper.getBean(controllerClass);

            Param param = null;
            Object result = null;
            Method method = handler.getActionMethod();

            if (UploadHelper.isMultipart(req)) {
                param = UploadHelper.createParam(req);
            } else {
                param = RequestHelper.createParam(req);
            }

            if (param.isEmpty()) {
                result = ReflectionUtil.invokeMethod(controllerInstance, method);
            } else {
                result = ReflectionUtil.invokeMethod(controllerInstance, method, param);

            }

            if (result instanceof View) {
                handleViewResult(req, resp, (View) result);
            } else if (result instanceof Data) {
                handleDataResult(resp, (Data) result);
            }
        }
    }

    private void handleDataResult(HttpServletResponse resp, Data result) throws IOException {
        Data data = result;
        Object model = data.getModel();
        if (model != null) {
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            PrintWriter print = resp.getWriter();
            print.write(JsonUtil.Object2String(model));

            print.flush();
            print.close();
        }
    }

    private void handleViewResult(HttpServletRequest req, HttpServletResponse resp, View result) throws IOException, ServletException {
        View view = result;
        String path = view.getPath();
        if (StringUtil.isNotEmpty(path)) {
            if (path.startsWith("/")) {
                resp.sendRedirect(req.getContextPath() + path);
            } else {
                Map<String, Object> model = view.getModel();
                for (Map.Entry<String, Object> entry : model.entrySet()) {
                    req.setAttribute(entry.getKey(), entry.getValue());
                }

                req.getRequestDispatcher(ConfigHelper.getAppJspPath() + path).forward(req, resp);
            }
        }
    }
}
