package cn.xqhuang.dps.service.impl;


import java.io.IOException;

import cn.xqhuang.dps.model.ElasticSearchRequest;
import cn.xqhuang.dps.model.FilterCommand;
import cn.xqhuang.dps.model.GeoDistance;
import cn.xqhuang.dps.service.SearchService;
import cn.xqhuang.dps.util.ToolUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.join.query.HasChildQueryBuilder;
import org.elasticsearch.join.query.JoinQueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class SearchServiceImpl implements SearchService {
	
	@Autowired
	RestHighLevelClient restHighLevelClient;
	
	@Override
	public SearchResponse queryString(ElasticSearchRequest request) {
		SearchRequest searchRequest = new SearchRequest(request.getQuery().getIndexName());
		// 如果关键词为空，则返回所有
		String content = request.getQuery().getKeyWords();
		Integer rows = request.getQuery().getRows();
		if (rows == null || rows == 0) {
			rows = 10;
		}
		Integer start = request.getQuery().getStart();
		if (content == null || "".equals(content)) {
			// 查询所有
			content = "*";
		}
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		// 提取搜索内容
		BoolQueryBuilder builder;
        if("*".equalsIgnoreCase(content)){
            builder = QueryBuilders.boolQuery().must(QueryBuilders.queryStringQuery(content).defaultOperator(Operator.AND));
        }else {
            builder = QueryBuilders.boolQuery().must(QueryBuilders.queryStringQuery(ToolUtils.handKeyword(content)).defaultOperator(Operator.AND));
        }
		// 提取过滤条件
		FilterCommand filter = request.getFilter();
		if (filter != null) {
			if (filter.getStartDate()!=null&&filter.getEndDate()!=null) {
					builder.filter(QueryBuilders.rangeQuery(filter.getField()).from(filter.getStartDate())
							.to(filter.getEndDate()));
			}
		}
		// 排序
		if(StringUtils.isNoneBlank(request.getQuery().getSort())){
			searchSourceBuilder.sort(request.getQuery().getSort(), SortOrder.ASC);
	    }
		// 查询全部
		searchSourceBuilder.trackTotalHits(true);
	    searchSourceBuilder.query(builder);
	    // 处理高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("*");
        searchSourceBuilder.highlighter(highlightBuilder);
		searchSourceBuilder.from(start);
		searchSourceBuilder.size(rows);
		
		searchRequest.source(searchSourceBuilder);
		SearchResponse searchResponse = null;
		try {
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return searchResponse;
	}


	@Override
	public SearchResponse termSearch(String index, String field, String term) {
		SearchRequest searchRequest = new SearchRequest(index);
		BoolQueryBuilder builder = QueryBuilders.boolQuery().must(QueryBuilders.termQuery(field, term));
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(builder);
		searchRequest.source(searchSourceBuilder);
		SearchResponse searchResponse = null;
		try {
			 searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return searchResponse;
	}
	
	@Override
	public SearchResponse matchAllSearch(String index) {
		SearchRequest searchRequest = new SearchRequest(index);
		BoolQueryBuilder builder = QueryBuilders.boolQuery().must(QueryBuilders.matchAllQuery());
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(builder);
		searchSourceBuilder.trackTotalHits(true);
		searchRequest.source(searchSourceBuilder);
		SearchResponse searchResponse = null;
		try {
			 searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return searchResponse;
	}


	@Override
	public SearchResponse geoDistanceSearch(String index, GeoDistance geo, Integer pagenum, Integer pagesize) {
		 SearchRequest searchRequest = new SearchRequest("shop");
	        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
	        BoolQueryBuilder builder;
	        builder = QueryBuilders.boolQuery().must(QueryBuilders.geoDistanceQuery("location")
	                .point(geo.getLatitude(), geo.getLongitude())
	                .distance(geo.getDistance(), DistanceUnit.KILOMETERS));
	        SearchResponse searchResponse = null;
	        try {
	            searchSourceBuilder.query(builder);
	            searchSourceBuilder.trackTotalHits(true);
	            searchRequest.source(searchSourceBuilder);
	            int start = (pagenum - 1) * pagesize;
	            searchSourceBuilder.from(start);
	            searchSourceBuilder.size(pagesize);
	            searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        return searchResponse;
	}
	
	@Override
	public SearchResponse matchNestedObjectSearch(String path, String index, String field, String value, Integer pageNum, Integer pageSize) {
		SearchRequest searchRequest = new SearchRequest(index);
		BoolQueryBuilder builder = QueryBuilders.boolQuery()
				.must(QueryBuilders.nestedQuery(path, QueryBuilders.matchQuery(field, value), ScoreMode.None));
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.trackTotalHits(true);
		searchSourceBuilder.query(builder);
		int start = (pageNum - 1) * pageSize;
        searchSourceBuilder.from(start);
        searchSourceBuilder.size(pageSize);
        // 处理高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("*");
        searchSourceBuilder.highlighter(highlightBuilder);
		searchRequest.source(searchSourceBuilder);
		SearchResponse searchResponse = null;
		try {
			 searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return searchResponse;
	}


	@Override
	public SearchResponse scrollQueryString(ElasticSearchRequest request) {
		SearchRequest searchRequest = new SearchRequest(request.getQuery().getIndexName());
		// 如果关键词为空，则返回所有
		String content = request.getQuery().getKeyWords();
		Integer rows = request.getQuery().getRows();
		if (rows == null || rows == 0) {
			rows = 10;
		}
		Integer start = request.getQuery().getStart();
		if (content == null || "".equals(content)) {
			// 查询所有
			content = "*";
		}
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		// 提取搜索内容
		BoolQueryBuilder builder;
        if("*".equalsIgnoreCase(content)){
            builder = QueryBuilders.boolQuery().must(QueryBuilders.queryStringQuery(content).defaultOperator(Operator.AND));
        }else {
            builder = QueryBuilders.boolQuery().must(QueryBuilders.queryStringQuery(ToolUtils.handKeyword(content)).defaultOperator(Operator.AND));
        }
		// 提取过滤条件
		FilterCommand filter = request.getFilter();
		if (filter != null) {
			if (filter.getStartDate()!=null&&filter.getEndDate()!=null) {
					builder.must(QueryBuilders.constantScoreQuery(QueryBuilders.rangeQuery(filter.getField()).from(filter.getStartDate()).to(filter.getEndDate())));
			}
		}
		// 排序
		if(StringUtils.isNoneBlank(request.getQuery().getSort())){
			searchSourceBuilder.sort(request.getQuery().getSort(), SortOrder.ASC);
	    }
	    searchSourceBuilder.query(builder);
	    searchSourceBuilder.trackTotalHits(true);
	    // 处理高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("*");
        searchSourceBuilder.highlighter(highlightBuilder);
        
		searchSourceBuilder.size(rows);
		
		SearchResponse searchResponse = null;
		
		if (request.getQuery().getScrollId() == null) {
			searchSourceBuilder.from(0);
			searchRequest.scroll(TimeValue.timeValueMinutes(5L));
			searchRequest.source(searchSourceBuilder);
			try {
				searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			SearchScrollRequest scrollRequest = new SearchScrollRequest(request.getQuery().getScrollId());
			scrollRequest.scroll(TimeValue.timeValueMinutes(5L));
			try {
				searchResponse = restHighLevelClient.scroll(scrollRequest, RequestOptions.DEFAULT);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return searchResponse;
	}
	
	@Override
	public SearchResponse hasChildSearch(String childType, String index, String field, String value, Integer pagenum, Integer pagesize) {
		SearchRequest searchRequest = new SearchRequest(index);
		HasChildQueryBuilder builder;
		if ( value != null && !("".equals(value))) {
			builder = JoinQueryBuilders.hasChildQuery(childType, QueryBuilders.termQuery(field, value), ScoreMode.None);
		} else {
			builder = JoinQueryBuilders.hasChildQuery(childType, QueryBuilders.matchAllQuery(), ScoreMode.None);
		}
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(builder);
		searchSourceBuilder.trackTotalHits(true);
		int start = (pagenum - 1) * pagesize;
        searchSourceBuilder.from(start);
        searchSourceBuilder.size(pagesize);
		searchRequest.source(searchSourceBuilder);
		SearchResponse searchResponse = null;
		try {
			 searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return searchResponse;
	}


	@Override
	public SearchResponse hasParentSearch(String parentType, String index, String field, String value, Integer pagenum, Integer pagesize) {
		SearchRequest searchRequest = new SearchRequest(index);
		QueryBuilder builder;
		if ( value != null && !("".equals(value))) {
			builder = JoinQueryBuilders.hasParentQuery(parentType, QueryBuilders.termQuery(field, value), false);
		} else {
			builder = JoinQueryBuilders.hasParentQuery(parentType, QueryBuilders.matchAllQuery(), false);
		}
		SearchSourceBuilder searchSourceBuilder = SearchSourceBuilder.searchSource()
						.query(builder).
						trackTotalHits(true)
				.from((pagenum - 1) * pagesize)
						.size(pagesize);
		searchRequest.source(searchSourceBuilder);
		SearchResponse searchResponse = null;
		try {
			 searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return searchResponse;
	}
}
