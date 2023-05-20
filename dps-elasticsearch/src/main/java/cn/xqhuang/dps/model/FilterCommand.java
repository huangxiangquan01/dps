package cn.xqhuang.dps.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class FilterCommand {

	String startDate;
	
	String endDate;
	
	String field;
}
