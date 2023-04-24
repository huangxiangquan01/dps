package cn.xqhuang.dps.manager;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class DataSourceManager {

    public static ConcurrentHashMap<String, DataSource> dataSourceConcurrentHashMap = new ConcurrentHashMap<>();

    public void put(String name, DataSource dataSource) {
        dataSourceConcurrentHashMap.put(name, dataSource);
    }

    public DataSource get(String name) {
        return dataSourceConcurrentHashMap.get(name);
    }

    public Boolean hasDataSource(String name) {
        return  dataSourceConcurrentHashMap.contains(name);
    }

    public void remove(String name) {
        dataSourceConcurrentHashMap.remove(name);
    }

    public void closeDataSource(String name) {
        if (hasDataSource(name)) {
            try {
                dataSourceConcurrentHashMap.get(name).getConnection().close();
            } catch (Exception e) {

            }
        }
    }

    public Collection<DataSource> all() {
        return dataSourceConcurrentHashMap.values();
    }
}