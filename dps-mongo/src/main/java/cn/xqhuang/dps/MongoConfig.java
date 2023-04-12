package cn.xqhuang.dps;

import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

// @Configuration
public class MongoConfig {

    // @Bean
    public MongoClient mongoClient() {
        return new MongoClient("mongodb://127.0.0.1:27017");
    }

    // @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), "test");
    }
}
