package cn.xqhuang.dps;

import com.anwen.mongo.config.OverrideMongoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = OverrideMongoConfiguration.class)
public class MongoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MongoApplication.class);
    }
}
