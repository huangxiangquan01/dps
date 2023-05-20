package cn.xqhuang.dps.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeoDistance {
	// 经度
	private Double longitude;
	
	// 纬度
	private Double latitude;
	
	// 检索半径
	private Double distance;

	private Integer pageNum;

	private Integer pagesize;
}
