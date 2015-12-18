/*
 * Copyright (c) 2015. Enum.Lin all rights reserved.
 */

package com.enumlin.core.helper;

import com.enumlin.core.utils.CollectionUtil;
import com.enumlin.core.utils.ProPertiesUtil;
import com.enumlin.core.utils.StringUtil;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/*
 * 数据库工具类
 *
 * @author   Enum.Lin
 * @version  1.0
 * @since    2015-11-30
 * 
 */
public class DatabaseHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseHelper.class);
    private static final QueryRunner QUERY_RUNNER = new QueryRunner();
    private static final ThreadLocal<Connection> CONNECTION_HOLDER = new ThreadLocal<>();
    private static final BasicDataSource DATA_SOURCE;

    static {
        Properties pro = ProPertiesUtil.load("dbconfig.properties");
        DATA_SOURCE = new BasicDataSource();
        DATA_SOURCE.setDriverClassName(ProPertiesUtil.getString(pro, "jdbc.driver"));
        DATA_SOURCE.setUrl(ProPertiesUtil.getString(pro, "jdbc.url"));
        DATA_SOURCE.setUsername(ProPertiesUtil.getString(pro, "jdbc.username"));
        DATA_SOURCE.setPassword(ProPertiesUtil.getString(pro, "jdbc.password"));
    }

    public static Connection getConnection() {
        Connection conn = CONNECTION_HOLDER.get();

        try {
            if (conn == null) {
                conn = DATA_SOURCE.getConnection();
            }
        } catch (SQLException e) {
            LOGGER.error("get connection failure.", e);
            throw new RuntimeException(e);
        } finally {
            CONNECTION_HOLDER.set(conn);
        }

        return conn;
    }

    @Deprecated
    public static void closeConnection() {
        Connection conn = CONNECTION_HOLDER.get();
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                LOGGER.error("close connection failure.", e);
                throw new RuntimeException(e);
            } finally {
                CONNECTION_HOLDER.remove();
            }
        }
    }

    public static <T> List<T> queryEntityList(Class<T> entityClass, String sql, Object... params) {
        List<T> entityList = null;
        Connection conn = getConnection();
        try {
            entityList = QUERY_RUNNER.query(conn, sql, new BeanListHandler<T>(entityClass), params);
        } catch (SQLException e) {
            LOGGER.error("query entity list failure.", e);
            throw new RuntimeException(e);
        }

        return entityList;
    }

    public static <T> T queryEntity(Class<T> entityClass, String sql, Object... params) {
        Connection conn = getConnection();
        T result = null;
        try {
            result = QUERY_RUNNER.query(conn, sql, new BeanHandler<T>(entityClass), params);
        } catch (SQLException e) {
            LOGGER.error("Query entity failure.", e);
            throw new RuntimeException(e);
        }

        return result;
    }

    public static List<Map<String, Object>> executeQuery(String sql, Object... parmas) {
        List<Map<String, Object>> result = null;
        try {
            result = QUERY_RUNNER.query(getConnection(), sql, new MapListHandler(), parmas);
        } catch (SQLException e) {
            LOGGER.error("execute query failure.", e);
            throw new RuntimeException(e);
        }

        return result;
    }

    public static int executeUpdate(String sql, Object... params) {
        int count = 0;

        try {
            count = QUERY_RUNNER.update(getConnection(), sql, params);
        } catch (SQLException e) {
            LOGGER.error("execute update failure.", e);
            throw new RuntimeException(e);
        }

        return count;
    }

    /**
     * 插入
     *
     * @param entityClass
     * @param fieldMap
     * @param <T>
     * @return
     */
    public static <T> boolean insertEntity(Class<T> entityClass, Map<String, Object> fieldMap) {
        if (CollectionUtil.isEmpty(fieldMap)) {
            LOGGER.error("can not insert entity : fieldMap is empty.");
            return false;
        }

        String sql = "insert into " + getTabelName(entityClass);
        StringBuilder colums = new StringBuilder(" (");
        StringBuilder values = new StringBuilder(" (");

        for (String key : fieldMap.keySet()) {
            colums.append(key).append(", ");
            values.append("?, ");
        }

        colums.replace(colums.lastIndexOf(", "), colums.length(), ") ");
        values.replace(values.lastIndexOf(", "), values.length(), ") ");
        sql += colums + "values" + values;
        Object[] params = fieldMap.values().toArray();

        return executeUpdate(sql, params) == 1;
    }

    /**
     * 更新
     *
     * @param entityClass
     * @param id
     * @param fieldMap
     * @param <T>
     * @return
     */
    public static <T> boolean updateEntity(Class<T> entityClass, long id, Map<String, Object> fieldMap) {
        if (CollectionUtil.isEmpty(fieldMap)) {
            LOGGER.error("can not update entity: fileMap is empty.");
            return false;
        }

        String sql = "update " + getTabelName(entityClass) + " set ";
        StringBuilder colums = new StringBuilder();
        for (String fieldName : fieldMap.keySet()) {
            if (StringUtil.isEmpty(fieldName)) continue;
            colums.append(fieldName).append("=?, ");
        }

        sql += colums.substring(0, colums.lastIndexOf(",")) + " where id=?";
        List<Object> parmaList = new ArrayList<>();
        parmaList.addAll(fieldMap.values());
        parmaList.add(id);
        Object[] params = parmaList.toArray();
        LOGGER.info(sql);

        return executeUpdate(sql, params) == 1;
    }

    /**
     * 删除
     *
     * @param entityClass
     * @param id
     * @param <T>
     * @return
     */
    public static <T> boolean deleteEntity(Class<T> entityClass, long id) {
        String sql = "delelte from " + getTabelName(entityClass) + " where id=?";
        return executeUpdate(sql, id) == 1;
    }

    /**
     * 执行 SQL文件
     *
     * @param filePath
     */
    public static void executeSqlFile(String filePath) {
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        try {
            String sql = null;
            while ((sql = reader.readLine()) != null) {
                executeUpdate(sql);
            }
        } catch (IOException e) {
            LOGGER.error("execute sqlfile failure.", e);
            throw new RuntimeException(e);
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                LOGGER.error("close reader failure.", e);
            }
        }
    }

    /**
     * 开启当前线程的事务
     */
    public static void beginTransaction() {
        Connection connection = getConnection();
        if (connection != null) {
            try {
                connection.setAutoCommit(false);
            } catch (SQLException e) {
                LOGGER.error("open transaction failure.", e);
            } finally {
                CONNECTION_HOLDER.set(connection);
            }
        }
    }

    /**
     * 提交事务
     */
    public static void commitTransaction() {
        Connection connection = getConnection();
        if (connection != null) {
            try {
                connection.commit();
                connection.close();
            } catch (SQLException e) {
                LOGGER.error("commit transaction failure.", e);
            } finally {
                CONNECTION_HOLDER.remove();
            }
        }
    }

    /**
     * 回滚事务
     */
    public static void rollbackTransaction() {
        Connection connection = getConnection();
        if (connection != null) {
            try {
                connection.rollback();
                connection.close();
            } catch (SQLException e) {
                LOGGER.error("rollback transaction failure.", e);
            } finally {
                CONNECTION_HOLDER.remove();
            }
        }
    }

    private static <T> String getTabelName(Class<T> entityClass) {
        return entityClass.getSimpleName();
    }
}
