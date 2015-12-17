/*
 * Copyright (c) 2015. Enum.Lin all rights reserved.
 */

package com.enumlin.aop.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/*
 * 基于 CGLib 的动态代理类
 *
 * @author   Enum.Lin
 * @version  1.0
 * @since    2015-12-05
 * 
 */
public class CGLibProxy implements MethodInterceptor {

    private static final CGLibProxy proxy = new CGLibProxy();

    public static CGLibProxy getInstance() {
        return proxy;
    }

    private CGLibProxy() {}

    public <T> T getProxy(Class<T> target) {
        return (T) Enhancer.create(target, this);
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        before();
        Object result = methodProxy.invokeSuper(o, objects);
        after();
        return result;
    }

    protected void after() {System.out.println("invoke method after.");}

    protected void before() {System.out.println("invoke method before.");}
}
