package cn.xqhuang.dps.entity;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("演员表")
public class Actor implements Serializable{

	@ApiModelProperty("主键")
	private short id;
	@ApiModelProperty("名字")
	private String firstName;
	@ApiModelProperty("姓氏")
	private String lastName;
	@ApiModelProperty("最后更新日期")
	private String lastUpdate;
}
