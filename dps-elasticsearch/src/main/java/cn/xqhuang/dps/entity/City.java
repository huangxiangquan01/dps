package cn.xqhuang.dps.entity;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class City {

	private Short cityId;

	private String city;

	private String lastUpdate;

	private Country country;

}
