/*
 * Copyright (c) 2015. Enum.Lin all rights reserved.
 */

package com.enumlin.core.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/*
 * 封装请求类
 *
 * @author   Enum.Lin
 * @version  1.0
 * @since    2015-12-02
 * 
 */
public class Request {
    /**
     * 请求方法
     */
    private String requestName;

    /**
     * 请求路径
     */
    private String requestPath;

    public Request(String requestName, String requestPath) {
        this.requestName = requestName;
        this.requestPath = requestPath;
    }

    public String getRequestName() {
        return requestName;
    }

    public String getRequestPath() {
        return requestPath;
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
