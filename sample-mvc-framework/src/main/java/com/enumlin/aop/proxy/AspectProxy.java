/*
 * Copyright (c) 2015. Enum.Lin all rights reserved.
 */

package com.enumlin.aop.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/*
 * 切面模板
 *
 * @author   Enum.Lin
 * @version  1.0
 * @since    2015-12-16
 * 
 */
public abstract class AspectProxy implements Proxy {
    private static final Logger LOGGER = LoggerFactory.getLogger(AspectProxy.class);

    /**
     * 执行链式代理
     *
     * @param proxyChain
     * @return
     * @throws Throwable
     */
    @Override
    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result = null;
        Class<?> targetClass = proxyChain.getTargetClass();
        Method targetMethod = proxyChain.getTargetMethod();
        Object[] methodParas = proxyChain.getMethodParas();

        begin();
        try {
            if (intercept(targetClass, targetMethod, methodParas)) {
                before(targetClass, targetMethod, methodParas);
                result = proxyChain.doProxyChain();
                after(targetClass, targetMethod, methodParas);
            } else {
                result = proxyChain.doProxyChain();
            }
        } catch (Exception e) {
            LOGGER.error("proxy failure", e);
            error(targetClass, targetMethod, methodParas, e);
            throw e;
        } finally {
            end();
        }
        return result;
    }

    protected void error(Class<?> targetClass, Method targetMethod, Object[] methodParas, Throwable e) {

    }

    protected void after(Class<?> targetClass, Method targetMethod, Object[] methodParas) throws Throwable {

    }

    protected void before(Class<?> targetClass, Method targetMethod, Object[] methodParas) throws Throwable {

    }

    protected boolean intercept(Class<?> targetClass, Method targetMethod, Object[] methodParas) {
        return true;
    }

    protected void begin() {

    }

    protected void end() {

    }
}
