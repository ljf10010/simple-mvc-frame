/*
 * Copyright (c) 2015. Enum.Lin all rights reserved.
 */

package com.enumlin.core.bean;

import java.util.HashMap;
import java.util.Map;

/*
 * 视图类
 *
 * @author   Enum.Lin
 * @version  1.0
 * @since    2015-12-02
 * 
 */
public class View {
    /**
     * 视图路径
     */
    private String path;

    /**
     * 视图数据
     */
    private Map<String, Object> model;

    public View(String path) {
        this.path = path;
        model = new HashMap<>();
    }

    public View addModel(String key, Object value) {
        model.put(key, value);
        return this;
    }

    public String getPath() {
        return path;
    }

    public Map<String, Object> getModel() {
        return model;
    }
}
