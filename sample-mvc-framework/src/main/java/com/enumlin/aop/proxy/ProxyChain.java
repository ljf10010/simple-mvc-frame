/*
 * Copyright (c) 2015. Enum.Lin all rights reserved.
 */

package com.enumlin.aop.proxy;

import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/*
 * 代理链
 *
 * @author   Enum.Lin
 * @version  1.0
 * @since    2015-12-16
 * 
 */
public class ProxyChain {
    private final Class<?> targetClass;
    private final Object targetObj;
    private final Method targetMethod;
    private final MethodProxy methodProxy;
    private final Object[] methodParas;

    private List<Proxy> proxyList = new ArrayList<>();
    private int proxyIndex = 0;

    public ProxyChain(Object[] methodParas, Class<?> targetClass, Object targetObj,
                      Method targetMethod, MethodProxy methodProxy, List<Proxy> proxyList) {
        this.methodParas = methodParas;
        this.targetClass = targetClass;
        this.targetObj = targetObj;
        this.targetMethod = targetMethod;
        this.methodProxy = methodProxy;
        this.proxyList = proxyList;
    }

    public Object[] getMethodParas() {
        return methodParas;
    }

    public Method getTargetMethod() {
        return targetMethod;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public Object doProxyChain() throws Throwable {
        Object methodResult = null;

        if (proxyIndex < proxyList.size())
            methodResult = proxyList.get(proxyIndex++).doProxy(this);
        else
            methodResult = methodProxy.invokeSuper(targetObj, methodParas);

        return methodResult;
    }
}
