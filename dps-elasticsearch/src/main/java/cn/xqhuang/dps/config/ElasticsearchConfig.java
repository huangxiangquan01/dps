package cn.xqhuang.dps.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author huangxq
 * @date 2022/11/1219:59
 */
@Configuration
public class ElasticsearchConfig {
    @Bean
    public RestHighLevelClient configRestHighLevelClient(){
        return new RestHighLevelClient(RestClient.builder(HttpHost.create("http://127.0.0.1:9200")));
    }
}
