package cn.xqhuang.dps.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BucketResult {

	String visitTime;
	
	Long docNumber;
	
	public BucketResult(String visitTime, Long docNumber) {
		this.visitTime = visitTime;
		this.docNumber = docNumber;
	}
}
