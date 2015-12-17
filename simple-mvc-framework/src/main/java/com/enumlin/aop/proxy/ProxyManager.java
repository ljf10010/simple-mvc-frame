/*
 * Copyright (c) 2015. Enum.Lin all rights reserved.
 */

package com.enumlin.aop.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

/*
 * 代理管理器
 *
 * @author   Enum.Lin
 * @version  1.0
 * @since    2015-12-16
 * 
 */
public class ProxyManager {
    public static <T> T createProxy(final Class<?> targetClass, final List<Proxy> proxyList) {
        return (T) Enhancer.create(targetClass, new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                return new ProxyChain(objects, o.getClass(), o, method, methodProxy, proxyList);
            }
        });
    }
}
