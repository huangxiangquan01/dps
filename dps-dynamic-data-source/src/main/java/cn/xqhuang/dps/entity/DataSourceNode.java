package cn.xqhuang.dps.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author huangxq
 * @description: 数据库
 * @date 2023/4/6 09:48
 */
@Getter
@Setter
public class DataSourceNode {

    private String id;

    private String name;

    private String url;

    private String port;

    private String username;

    private String password;

}
