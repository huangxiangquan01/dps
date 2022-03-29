package cn.xqhuang.dps.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HelloStarter {

    private String name;

    public HelloStarter(String name) {
        this.name = name;
    }
}
