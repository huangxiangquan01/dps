package cn.xqhuang.dps.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author huangxq
 * @description:
 * @date 2023/4/11 14:47 星期二
 */

@Getter
@Setter
@ApiModel("用户")
public class User {

    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("年龄")
    private Integer age;
}
