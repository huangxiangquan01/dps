package cn.xqhuang.dps.service;

import cn.xqhuang.dps.model.QueryCommand;
import cn.xqhuang.dps.model.RangeQuery;
import cn.xqhuang.dps.model.ResultData;

public interface AggService {
	// 词条聚集
	ResultData termsAgg(QueryCommand content) throws Exception;
	// 范围聚集
	ResultData rangeAgg(RangeQuery content) throws Exception;
	// 直方图聚集
	ResultData histogramAgg(QueryCommand content) throws Exception;
	// 日期直方图聚集
	ResultData dateHistogramAgg(QueryCommand content) throws Exception;
	// 嵌套对象词条聚集
	ResultData nestedTermsAgg() throws Exception;
}