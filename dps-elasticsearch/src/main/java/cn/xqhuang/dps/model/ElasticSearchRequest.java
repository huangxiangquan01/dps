package cn.xqhuang.dps.model;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;


/**
 * 搜索请求参数
 * @author huangxq
 *
 */
@Getter
@Setter
public class ElasticSearchRequest {
	// 查询条件
	private QueryCommand query;
	// 过滤条件
	private FilterCommand filter;
}
