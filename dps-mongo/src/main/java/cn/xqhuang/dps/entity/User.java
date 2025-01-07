package cn.xqhuang.dps.entity;

import com.anwen.mongo.annotation.ID;
import lombok.Data;

/**
 * @author huangxiangquan
 */
@Data
public class User {
    @ID
    private String id;
    private String name;
    private Long age;
    private String email;
}
