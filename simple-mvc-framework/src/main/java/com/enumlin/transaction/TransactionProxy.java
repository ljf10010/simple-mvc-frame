/*
 * Copyright (c) 2015. Enum.Lin all rights reserved.
 */

package com.enumlin.transaction;

import com.enumlin.aop.proxy.Proxy;
import com.enumlin.aop.proxy.ProxyChain;
import com.enumlin.core.helper.DatabaseHelper;
import com.enumlin.transaction.annotation.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/*
 * 事务代理
 *
 * @author   Enum.Lin
 * @version  1.0
 * @since    2015-12-18
 * 
 */
public class TransactionProxy implements Proxy {
    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionProxy.class);

    private static final ThreadLocal<Boolean> FLAG_HOLDER = new ThreadLocal<Boolean>() {
        @Override
        protected Boolean initialValue() {
            return false;
        }
    };


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

        Method method = proxyChain.getTargetMethod();
        if (!FLAG_HOLDER.get() && method.isAnnotationPresent(Transaction.class)) {
            FLAG_HOLDER.set(true);
            try {
                DatabaseHelper.beginTransaction();
                result = proxyChain.doProxyChain();
                DatabaseHelper.commitTransaction();
            } catch (Exception e) {
                DatabaseHelper.rollbackTransaction();
                LOGGER.debug("rollback transaction.", e);
            } finally {
                FLAG_HOLDER.remove();
            }
        } else {
            result = proxyChain.doProxyChain();
        }
        return result;
    }
}
