package cn.xqhuang.dps.service;


import cn.xqhuang.dps.model.ElasticSearchRequest;
import cn.xqhuang.dps.model.GeoDistance;
import org.elasticsearch.action.search.SearchResponse;


public interface SearchService {
	/**
	 * 多字段搜索-普通分页
	 * @param: request
	 * @return:
	 */
	SearchResponse queryString(ElasticSearchRequest request);
	/**
	 * 多字段搜索-滚动分页
	 * @param: request
	 * @return:
	 */
	SearchResponse scrollQueryString(ElasticSearchRequest request);
	
	/**
	 * 精准搜索
	 */
	SearchResponse termSearch(String index, String field,String term);
	
	/**
	 * 搜索全部
	 */
	SearchResponse matchAllSearch(String index);
	
	/**
	 * 经纬度搜索
	 */
	SearchResponse geoDistanceSearch(String index, GeoDistance geo, Integer pageNum, Integer pageSize);
	
	/**
	 * 搜索嵌套对象
	 */
	SearchResponse matchNestedObjectSearch(String path, String index, String field, String value, Integer pagenum, Integer pagesize);

	/**
	 * join查询：以子查父
	 */
	SearchResponse hasChildSearch(String childType, String index, String field, String value, Integer pagenum, Integer pagesize);
	
	/**
	 * join查询：以父查子
	 */
	SearchResponse hasParentSearch(String parentType, String index, String field, String value, Integer pagenum, Integer pagesize);

}
