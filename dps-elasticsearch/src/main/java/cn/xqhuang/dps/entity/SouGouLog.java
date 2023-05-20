package cn.xqhuang.dps.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SouGouLog {

	private Integer id;
	
    private String visitTime;

    private String userid;

    private String keywords;

    private Integer rank;

    private Integer clickNum;

    private String url;
    
}