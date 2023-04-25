/*
 * Copyright © 2018 organization baomidou
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.xqhuang.dps.provider;

import cn.xqhuang.dps.config.DataSourceProperty;
import cn.xqhuang.dps.config.DynamicDataSourceProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author huangxq
 */
@Slf4j
@Component
public class DynamicDataSourceProvider {

    /**
     * 所有数据源
     */
    @Resource
    private DynamicDataSourceProperties dataSourcePropertiesMap;


    public Map<String, DataSource> loadDataSources() {
        return createDataSourceMap(dataSourcePropertiesMap.getDatasource());
    }

    private Map<String, DataSource> createDataSourceMap(Map<String, DataSourceProperty> dataSourcePropertiesMap) {
        Map<String, DataSource> dataSourceMap = new HashMap<>(dataSourcePropertiesMap.size() * 2);
        for (Map.Entry<String, DataSourceProperty> item : dataSourcePropertiesMap.entrySet()) {
            String dsName = item.getKey();
            DataSourceProperty dataSourceProperty = item.getValue();

            dataSourceMap.put(dsName, DataSourceBuilder.create()
                            .url(dataSourceProperty.getUrl())
                            .type(dataSourceProperty.getType())
                            .username(dataSourceProperty.getUsername())
                            .password(dataSourceProperty.getPassword())
                    .build());
        }
        return dataSourceMap;
    }
}
