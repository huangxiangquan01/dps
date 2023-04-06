package cn.xqhuang.dps.manager;

import javax.sql.DataSource;
import java.util.Collection;

public interface DataSourceManager {

    void put(String var1, DataSource var2);
 
    DataSource get(String var1);
 
    Boolean hasDataSource(String var1);
 
    void remove(String var1);
 
    void closeDataSource(String var1);
 
    Collection<DataSource> all();
}