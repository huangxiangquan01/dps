package cn.xqhuang.dps.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import cn.xqhuang.dps.model.*;
import cn.xqhuang.dps.service.SearchService;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "搜索接口")
@RestController
public class SearchController {

	@Resource
	private SearchService searchService;

	@RequestMapping(value = "/souGouLog", method = RequestMethod.GET)
	public String souGouLog() {
		return "souGouLog";
	}
	
	@RequestMapping(value = "/distance", method = RequestMethod.GET)
	public String distance() {
		return "distance";
	}	

	@RequestMapping(value = "/city", method = RequestMethod.GET)
	public String city() {
		return "city";
	}
	
	@RequestMapping(value = "/hasChild", method = RequestMethod.GET)
	public String hasChild() {
		return "hasChild";
	}
	
	@RequestMapping(value = "/hasParent", method = RequestMethod.GET)
	public String hasParent() {
		return "hasParent";
	}
	
    @ApiOperation("获取一个日志数据")
	@RequestMapping(value = "/souGouLog/{id}", method = RequestMethod.GET)
    public ResultData termSearch(@PathVariable String id) throws Exception{
    	SearchResponse rsp = searchService.termSearch("souGouLog", "id", id);
    	SearchHit[] searchHits = rsp.getHits().getHits();
    	List<Object> data = new ArrayList<>();
    	for (SearchHit hit : searchHits) {
    		Map<String, Object> map = hit.getSourceAsMap();
    		data.add(map);
    	}
    	ResultData rd = new ResultData();
    	rd.setData(data);
    	return rd;
	}	
    
    @ApiOperation("获取日志数据的总数")
	@RequestMapping(value = "/souGouLogNumber", method = RequestMethod.GET)
    public ResultData souGouLogNumber() throws Exception {
    	SearchResponse rsp = searchService.matchAllSearch("souGouLog");
    	Long total = rsp.getHits().getTotalHits().value;
    	ResultData rd = new ResultData();
    	rd.setData(total);
    	return rd;
	}	    
	
	@ApiOperation("分页查询搜狗日志")
	@RequestMapping(value = "/souGouLog", method = RequestMethod.POST)
	public DataGrid<Object> listSouGouLog(@RequestParam(value="current") int current, @RequestParam(value="rowCount") int rowCount
			, @RequestParam(value="searchPhrase") String searchPhrase, @RequestParam(value="startDate",required=false) String startdate
			, @RequestParam(value="endDate",required=false) String enddate) {
		DataGrid<Object> grid = new DataGrid<Object>();
		List<Object> data = new ArrayList<>();
		ElasticSearchRequest request = new ElasticSearchRequest();
		QueryCommand query = new QueryCommand();
		query.setIndexName("souGouLog");
		if (StringUtils.isBlank(searchPhrase)) {
			query.setKeyWords("*");
		} else {
			query.setKeyWords(searchPhrase);
		}
		query.setRows(rowCount);
		query.setStart((current-1)*rowCount);
		query.setSort("id");
		request.setQuery(query);
		if (StringUtils.isNotBlank(startdate) || StringUtils.isNotBlank(enddate)) {
			FilterCommand filter = new FilterCommand();
			filter.setField("visitTime");
			filter.setStartDate(startdate);
			filter.setEndDate(enddate);
			request.setFilter(filter);
		}
		SearchResponse searchResponse = searchService.queryString(request);
		SearchHits hits = searchResponse.getHits();
		SearchHit[] searchHits = hits.getHits();
		for (SearchHit hit : searchHits) {
			Map<String, Object> highlights = new HashMap<String, Object>();
			Map<String, Object> map = hit.getSourceAsMap();
			// 获取高亮结果
			Map<String, HighlightField> highlightFields = hit.getHighlightFields();
			for (Map.Entry<String, HighlightField> entry : highlightFields.entrySet()) {
				String mapKey = entry.getKey();
				HighlightField mapValue = entry.getValue();
				Text[] fragments = mapValue.fragments();
				String fragmentString = fragments[0].string();
				highlights.put(mapKey, fragmentString);
			}
			map.put("highlight", highlights);
			data.add(map);
		}
		grid.setCurrent(current);
		grid.setRowCount(rowCount);
		grid.setRows(data);
		grid.setTotal(hits.getTotalHits().value);
		return grid;
	}	
	

	@ApiOperation("query_string全字段查找-普通分页版")
	@RequestMapping(value = "/queryString", method = RequestMethod.POST)
	public ResultData query_string(@RequestBody ElasticSearchRequest request) {
		// 搜索结果
		List<Object> data = new ArrayList<Object>();
		SearchResponse searchResponse = searchService.queryString(request);
		SearchHits hits = searchResponse.getHits();
		SearchHit[] searchHits = hits.getHits();
		for (SearchHit hit : searchHits) {
			Map<String, Object> highlights = new HashMap<String, Object>();
			Map<String, Object> map = hit.getSourceAsMap();
			// 获取高亮结果
			Map<String, HighlightField> highlightFields = hit.getHighlightFields();
			for (Map.Entry<String, HighlightField> entry : highlightFields.entrySet()) {
				String mapKey = entry.getKey();
				HighlightField mapValue = entry.getValue();
				Text[] fragments = mapValue.fragments();
				String fragmentString = fragments[0].string();
				highlights.put(mapKey, fragmentString);
			}
			map.put("highlight", highlights);
			data.add(map);
		}
		ResultData resultData = new ResultData();
		resultData.setQueryTime(new Date());
		resultData.setData(data);
		resultData.setNumberFound(hits.getTotalHits().value);
		resultData.setStart(request.getQuery().getStart());
		return resultData;
	}
	
	@ApiOperation("query_string全字段查找-滚动分页版")
	@RequestMapping(value = "/query_string/scroll", method = RequestMethod.POST)
	public ResultData scrollquery_string(@RequestBody ElasticSearchRequest request) {
		// 搜索结果
		List<Object> data = new ArrayList<Object>();
		SearchResponse searchResponse = searchService.scrollQueryString(request);
		SearchHits hits = searchResponse.getHits();
		SearchHit[] searchHits = hits.getHits();
		String scrollid = searchResponse.getScrollId();
		for (SearchHit hit : searchHits) {
			Map<String, Object> highlights = new HashMap<String, Object>();
			Map<String, Object> map = hit.getSourceAsMap();
			// 获取高亮结果
			Map<String, HighlightField> highlightFields = hit.getHighlightFields();
			for (Map.Entry<String, HighlightField> entry : highlightFields.entrySet()) {
				String mapKey = entry.getKey();
				HighlightField mapValue = entry.getValue();
				Text[] fragments = mapValue.fragments();
				String fragmentString = fragments[0].string();
				highlights.put(mapKey, fragmentString);
			}
			map.put("highlight", highlights);
			data.add(map);
		}
		ResultData resultData = new ResultData();
		resultData.setQueryTime(new Date());
		resultData.setData(data);
		resultData.setNumberFound(hits.getTotalHits().value);
		resultData.setStart(request.getQuery().getStart());
		resultData.setScrollId(scrollid);
		return resultData;
	}	

	@ApiOperation("经纬度搜索")
	@RequestMapping(value = "/geosearch", method = RequestMethod.POST)
	public DataTable<Object> geosearch(@RequestBody GeoDistance geo) {
		// 搜索结果
		List<Object> data = new ArrayList<>();
		SearchResponse searchResponse = searchService.geoDistanceSearch("shop", geo, geo.getPageNum(), geo.getPagesize());
		SearchHits hits = searchResponse.getHits();
		SearchHit[] searchHits = hits.getHits();
		for (SearchHit hit : searchHits) {
			Map<String, Object> map = hit.getSourceAsMap();
			data.add(map);
		}
		DataTable<Object> grid = new DataTable<Object>();
		grid.setDraw(UUID.randomUUID().toString());
		grid.setRecordsFiltered(hits.getTotalHits().value);
		grid.setRecordsTotal(hits.getTotalHits().value);
		grid.setData(data);
		grid.setLength(geo.getPagesize());
		return grid;
	}
	
	@ApiOperation("分页查询城市索引-嵌套父文档")
	@RequestMapping(value = "/city", method = RequestMethod.POST)
	public DataGrid<Object> listCitys(@RequestParam(value="current") int current, @RequestParam(value="rowCount") int rowCount
			,@RequestParam(value="searchPhrase") String searchPhrase) {
		DataGrid<Object> grid = new DataGrid<Object>();
		List<Object> data = new ArrayList<>();
		ElasticSearchRequest request = new ElasticSearchRequest();
		QueryCommand query = new QueryCommand();
		query.setIndexName("city");
		if (StringUtils.isBlank(searchPhrase)) {
			query.setKeyWords("*");
		} else {
			query.setKeyWords(searchPhrase);
		}
		query.setRows(rowCount);
		query.setStart((current-1)*rowCount);
		request.setQuery(query);
		SearchResponse searchResponse;
		if (StringUtils.isBlank(searchPhrase)) {
			searchResponse = searchService.queryString(request);
		} else {
			searchResponse = searchService.matchNestedObjectSearch("country", "city", "country.countryname", searchPhrase, current, rowCount);
		}
		SearchHits hits = searchResponse.getHits();
		SearchHit[] searchHits = hits.getHits();
		for (SearchHit hit : searchHits) {
			Map<String, Object> highlights = new HashMap<String, Object>();
			Map<String, Object> map = hit.getSourceAsMap();
			// 获取高亮结果
			Map<String, HighlightField> highlightFields = hit.getHighlightFields();
			for (Map.Entry<String, HighlightField> entry : highlightFields.entrySet()) {
				String mapKey = entry.getKey();
				HighlightField mapValue = entry.getValue();
				Text[] fragments = mapValue.fragments();
				String fragmentString = fragments[0].string();
				highlights.put(mapKey, fragmentString);
			}
			map.put("highlight", highlights);
			data.add(map);
		}
		grid.setCurrent(current);
		grid.setRowCount(rowCount);
		grid.setRows(data);
		grid.setTotal(hits.getTotalHits().value);
		return grid;
	}	

	@ApiOperation("join搜索-用城市搜国家")
	@RequestMapping(value = "/hasChild", method = RequestMethod.POST)
	public DataTable<Object> hasChild(@RequestBody JoinParams param) {
		// 搜索结果
		List<Object> data = new ArrayList<Object>();
		SearchResponse searchResponse = searchService.hasChildSearch("city", "cityJoinCountry",
				"cityName", param.getName(), param.getPageNum(), param.getPagesize());
		SearchHits hits = searchResponse.getHits();
		SearchHit[] searchHits = hits.getHits();
		for (SearchHit hit : searchHits) {
			Map<String, Object> map = hit.getSourceAsMap();
			data.add(map);
		}
		DataTable<Object> grid = new DataTable<Object>();
		grid.setDraw(UUID.randomUUID().toString());
		grid.setRecordsFiltered(hits.getTotalHits().value);
		grid.setLength(param.getPagesize());
		grid.setRecordsTotal(hits.getTotalHits().value);
		grid.setData(data);
		return grid;
	}
	
	@ApiOperation("join搜索-用国家搜城市")
	@RequestMapping(value = "/hasParent", method = RequestMethod.POST)
	public DataTable<Object> hasParent(@RequestBody JoinParams param) {
		// 搜索结果
		List<Object> data = new ArrayList<Object>();
		SearchResponse searchResponse = searchService.hasParentSearch("country",
				"cityJoinCountry", "country", param.getName(), param.getPageNum(), param.getPagesize());
		SearchHits hits = searchResponse.getHits();
		SearchHit[] searchHits = hits.getHits();
		for (SearchHit hit : searchHits) {
			Map<String, Object> map = hit.getSourceAsMap();
			data.add(map);
		}
		DataTable<Object> grid = new DataTable<Object>();
		grid.setDraw(UUID.randomUUID().toString());
		grid.setRecordsFiltered(hits.getTotalHits().value);
		grid.setRecordsTotal(hits.getTotalHits().value);
		grid.setLength(param.getPagesize());
		grid.setData(data);
		return grid;
	}
}
