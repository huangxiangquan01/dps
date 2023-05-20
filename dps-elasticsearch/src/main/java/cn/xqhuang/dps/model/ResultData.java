package cn.xqhuang.dps.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ResultData {

	Long numberFound;
	
	Integer start;
	
	Date queryTime;
	
	Object data;
	
	String scrollId;
    
}
