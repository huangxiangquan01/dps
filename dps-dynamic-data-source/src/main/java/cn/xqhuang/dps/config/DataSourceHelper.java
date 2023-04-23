package cn.xqhuang.dps.config;

import cn.xqhuang.dps.holder.DynamicDataSourceContextHolder;

public class DataSourceHelper {

    /**
     * 切换到自己的数据库
     */
    public static void changeToMy() {
        DynamicDataSourceContextHolder.clearDataSourceType();
        String dbName = "db1";
        DynamicDataSourceContextHolder.setDataSourceType(dbName);
    }

    /**
     * 切换到指定的数据库
     * @param datasource
     */
    public static void changeToSpecificDataSource(String datasource) {
        DynamicDataSourceContextHolder.clearDataSourceType();
        String dbName = datasource;
        DynamicDataSourceContextHolder.setDataSourceType(dbName);
    }

    /**
     * 重置链接到主库
     */
    public static void reset() {
        DynamicDataSourceContextHolder.clearDataSourceType();
    }
}