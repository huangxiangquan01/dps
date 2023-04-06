package cn.xqhuang.dps.service.impl;

import cn.xqhuang.dps.entity.DataSourceNode;
import cn.xqhuang.dps.mapper.DataSourceNodeMapper;
import cn.xqhuang.dps.service.DynamicDataSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author huangxq
 * @description: TODO
 * @date 2023/4/609:46
 */
@Component
public class DynamicDataSourceServiceImpl implements DynamicDataSourceService {

    public static ConcurrentHashMap<String, DataSource> dataSourceConcurrentHashMap = new ConcurrentHashMap<>();

    @Autowired
    private DataSourceNodeMapper dataSourceNodeMapper;

    @Override
    public ConcurrentHashMap<String, DataSource> getResolvedDataSources() {
        return null;
    }

    @Override
    public String getDbServerByDbName(String dbName) {
        return null;
    }

    @Override
    public List<DataSourceNode> getDbNodes() {
        return null;
    }

    @Override
    public void createDataSource(DataSourceNode node) {
        dataSourceConcurrentHashMap.put(node.getId(), DataSourceBuilder.create()
                .url(node.getUrl())
                .username(node.getUsername())
                .password(node.getPassword())
                .build());
    }
}
