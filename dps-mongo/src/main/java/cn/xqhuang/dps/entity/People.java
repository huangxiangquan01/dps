package cn.xqhuang.dps.entity;

import lombok.*;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class People {

    private String id;

    private String name;

    private Integer age;
}
