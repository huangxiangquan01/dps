package cn.xqhuang.dps.service;

import cn.xqhuang.dps.entity.DataSourceNode;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author huangxq
 * @description:
 * @date 2023/4/6 09:46
 */
public interface DynamicDataSourceService {

    ConcurrentHashMap<String, DataSource> getResolvedDataSources();

    String getDbServerByDbName(String dbName);

    List<DataSourceNode> getDbNodes();

    void createDataSource(DataSourceNode node);
}
