/*
 * Copyright (c) 2015. Enum.Lin all rights reserved.
 */

package com.enumlin.aop.proxy;

/*
 * 代理接口
 *
 * @author   Enum.Lin
 * @version  1.0
 * @since    2015-12-16
 * 
 */
public interface Proxy {
    /**
     * 执行链式代理
     *
     * @param proxyChain
     * @return
     * @throws Throwable
     */
    Object doProxy(ProxyChain proxyChain) throws Throwable;
}
