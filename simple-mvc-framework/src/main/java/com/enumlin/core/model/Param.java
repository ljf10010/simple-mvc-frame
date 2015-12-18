/*
 * Copyright (c) 2015. Enum.Lin all rights reserved.
 */

package com.enumlin.core.model;

import com.enumlin.core.utils.CastUtil;
import com.enumlin.core.utils.CollectionUtil;

import java.util.Map;

/*
 * 请求参数
 *
 * @author   Enum.Lin
 * @version  1.0
 * @since    2015-12-02
 * 
 */
public class Param {
    private Map<String, Object> paramMap;

    public Param(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    public long getLong(String name) {
        return CastUtil.castLong(paramMap.get(name));
    }

    public Map<String, Object> getMap() {
        return paramMap;
    }

    public boolean isEmpty() {
        return CollectionUtil.isEmpty(paramMap);
    }
}
