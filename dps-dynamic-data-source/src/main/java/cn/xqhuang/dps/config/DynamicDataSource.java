package cn.xqhuang.dps.config;

import cn.xqhuang.dps.entity.DataSourceNode;
import cn.xqhuang.dps.holder.DynamicDataSourceContextHolder;
import cn.xqhuang.dps.holder.MultiConnectionContextHolder;
import cn.xqhuang.dps.manager.DataSourceManager;
import cn.xqhuang.dps.proxy.ConnectProxy;
import cn.xqhuang.dps.service.DynamicDataSourceService;
import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.AbstractDataSource;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Primary
@Component
public class DynamicDataSource extends AbstractDataSource implements CommandLineRunner {

    private static final String DEFAULT_DB = "defaultDb";

    @Autowired
    private DataSource defaultDataSource;

    @Autowired
    private DataSourceManager dataSourceManager;

    @Autowired
    private DynamicDataSourceService dynamicDataSourceService;

    @Override
    public Connection getConnection() throws SQLException {
        Connection connection = DataSourceUtils.getConnection(this.determineTargetDataSource());
        return realGetConnection(connection);
    }

    /**
     *  真正获取链接的方法
     *  对获取数据库链接方法包装、保存到线程上下文
     *  @return Connection
     */
    private Connection realGetConnection(Connection connection) throws SQLException {
        connection.setCatalog(getCurrentDbName());
        // 检查是否开启了跨库事务
        boolean multiTransactionStatus = MultiConnectionContextHolder.getMultiTransactionStatus();
        if (multiTransactionStatus) {
            // 包装Connection对象，覆写commit方法，使mybatis自动提交失效
            ConnectProxy connectProxy = new ConnectProxy(connection);
            connectProxy.setAutoCommit(false);
            // 保存获取到的数据库链接到当前线程上下文
            MultiConnectionContextHolder.addConnection(connectProxy);
            // 开启跨库事务、返回包装后的数据库链接
            return connectProxy;
        }
        // 返回原生链接、不影响正常数据库自动操作提交
        return connection;
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        Connection connection = this.determineTargetDataSource().getConnection(username,password);
        return realGetConnection(connection);
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return iface.isInstance(this) ? (T) this : this.determineTargetDataSource().unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return iface.isInstance(this) || this.determineTargetDataSource().isWrapperFor(iface);
    }

    protected DataSource determineTargetDataSource() {
        DataSource dataSource = null;
        ConcurrentHashMap<String, DataSource> resolvedDataSources = dataSourceManager.dataSourceConcurrentHashMap;
        if (resolvedDataSources != null) {
            String lookupKey = getCurrentDbName();
            if (!StringUtils.isEmpty(lookupKey)) {
                dataSource = dataSourceManager.get(lookupKey);
            }
        }
        if (dataSource == null) {
            dataSource = defaultDataSource;
        }
        return dataSource;
    }

    /**
     * 查询当前线程上下文对应的库名
     *
     * @return
     */
    protected String getCurrentDbName() {
        String dbName = DynamicDataSourceContextHolder.getDataSourceType();
        if (StringUtils.isEmpty(dbName)) {
            dbName = DEFAULT_DB;
        }
        return dbName;
    }


    @Override
    public void run(String... args) throws Exception {
        QueryWrapper<DataSourceNode> wrapper = new QueryWrapper<>();
        List<DataSourceNode> nodes = dynamicDataSourceService.list(wrapper);

        dataSourceManager.put(DEFAULT_DB, defaultDataSource);
        nodes.forEach(node -> {
            dataSourceManager.put(node.getName(), DataSourceBuilder.create()
                    .url(node.getUrl())
                    .username(node.getUsername())
                    .password(node.getPassword())
                    .build());
        });
    }
}