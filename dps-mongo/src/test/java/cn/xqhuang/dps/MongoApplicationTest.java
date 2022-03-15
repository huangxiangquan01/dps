package cn.xqhuang.dps;

import cn.xqhuang.dps.entity.People;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Queue;


@SpringBootTest(classes = MongoApplication.class)
@RunWith(SpringRunner.class)
public class MongoApplicationTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void add() {
        People people = new People("2", "关羽", 50);
        mongoTemplate.save(people);
    }

    @Test
    public void findById() {
        People byId = mongoTemplate.findById("1", People.class);

        System.out.println(byId);
    }

    @Test
    public void findUpdated() {
        mongoTemplate.updateFirst(Query.query(Criteria.where("id").is("1")), Update.update("age", 37), People.class);
    }

    @Test
    public void remove() {
        mongoTemplate.remove(Query.query(Criteria.where("id").is("2")), People.class);
    }
}