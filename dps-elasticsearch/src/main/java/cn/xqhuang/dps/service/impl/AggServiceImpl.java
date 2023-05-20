package cn.xqhuang.dps.service.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import cn.xqhuang.dps.model.BucketResult;
import cn.xqhuang.dps.model.QueryCommand;
import cn.xqhuang.dps.model.RangeQuery;
import cn.xqhuang.dps.model.ResultData;
import cn.xqhuang.dps.service.AggService;
import cn.xqhuang.dps.util.ToolUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
import org.elasticsearch.search.aggregations.bucket.histogram.HistogramAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.nested.Nested;
import org.elasticsearch.search.aggregations.bucket.nested.NestedAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.range.DateRangeAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.range.Range;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AggServiceImpl implements AggService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Override
    public ResultData termsAgg(QueryCommand content) throws Exception {
        SearchRequest searchRequest = new SearchRequest(content.getIndexName());
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.matchAllQuery());
        TermsAggregationBuilder aggregation = AggregationBuilders.terms("countNumber").field(content.
                        getAggField()).size(10)
                .order(BucketOrder.key(true));
        searchSourceBuilder.trackTotalHits(true);
        searchSourceBuilder.query(queryBuilder).aggregation(aggregation);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        Aggregations result = searchResponse.getAggregations();
        Terms byCompanyAggregation = result.get("countNumber");
        List<? extends Terms.Bucket> bucketList = byCompanyAggregation.getBuckets();
        List<BucketResult> list = new ArrayList<>();
        for (Terms.Bucket bucket : bucketList) {
            BucketResult br = new BucketResult(bucket.getKeyAsString(), bucket.getDocCount());
            list.add(br);
        }
        ResultData resultData = new ResultData();
        resultData.setQueryTime(new Date());
        resultData.setData(list);
        resultData.setNumberFound(searchResponse.getHits().getTotalHits().value);
        resultData.setStart(content.getStart());
        return resultData;
    }

    @Override
    public ResultData rangeAgg(RangeQuery content) throws Exception {
        SearchRequest searchRequest = buildSearchRequest(content.getIndexName());
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.trackTotalHits(true);
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.matchAllQuery());
        // 聚集
        String dateField = content.getAggField();
        if (content.getRangeValues() != null && content.getRangeValues().size() > 0) {
            DateRangeAggregationBuilder dateRangeAggregationBuilder = AggregationBuilders
                    .dateRange("aggsName")
                    .field(dateField);
            for (int i = 0; i < content.getRangeValues().size(); i++) {
                String from =content.getRangeValues().get(i).getFrom();
                String to = content.getRangeValues().get(i).getTo();
                if(StringUtils.isNoneBlank(from)&&StringUtils.isBlank(to)){
                    dateRangeAggregationBuilder.addUnboundedFrom(from);
                }else if(StringUtils.isNoneBlank(to) &&StringUtils.isBlank(from)){
                    dateRangeAggregationBuilder.addUnboundedTo(to);
                }else if(StringUtils.isNoneBlank(from)&&StringUtils.isNoneBlank(to)){
                    dateRangeAggregationBuilder.addRange(from, to);
                }
            }
            searchSourceBuilder.query(queryBuilder).aggregation(dateRangeAggregationBuilder);
        } else {
            searchSourceBuilder.query(queryBuilder);
        }
        searchRequest.source(searchSourceBuilder);
        searchSourceBuilder.from(content.getStart());
        searchSourceBuilder.size(content.getRows());
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        Aggregations jsonAgg = searchResponse.getAggregations();
        Range range = jsonAgg.get("aggName");
        List<? extends Range.Bucket> bucketList = range.getBuckets();
        List<BucketResult> list = new ArrayList<>();
        for (Range.Bucket bucket : bucketList) {
        	BucketResult br = new BucketResult(bucket.getKeyAsString(), bucket.getDocCount());
            list.add(br);
        }
        ResultData resultData = new ResultData();
        resultData.setQueryTime(new Date());
        resultData.setData(list);
        resultData.setNumberFound(searchResponse.getHits().getTotalHits().value);
        resultData.setStart(content.getStart());
        return resultData;
    }

    @Override
    public ResultData histogramAgg(QueryCommand content) throws Exception {
        SearchRequest searchRequest = new SearchRequest(content.getIndexName());
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.matchAllQuery());
        searchSourceBuilder.trackTotalHits(true);
        // 聚集
        String dateField = content.getAggField();
        Integer step = content.getStep();
        if (step != null && dateField != null) {
            HistogramAggregationBuilder histogramAggregationBuilder = AggregationBuilders
                    .histogram("aggsName")
                    .field(dateField)
                    .interval(content.getStep())
                    .minDocCount(0L);
            searchSourceBuilder.query(queryBuilder).aggregation(histogramAggregationBuilder);
        } else {
            searchSourceBuilder.query(queryBuilder);
        }
        searchRequest.source(searchSourceBuilder);
        searchSourceBuilder.from(content.getStart());
        searchSourceBuilder.size(content.getRows());

        List<HashMap<String, Long>> list = new ArrayList<>();
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        Aggregations jsonAgg = searchResponse.getAggregations();
        Histogram dateHistogram =  jsonAgg.get("aggName");
        List<? extends Histogram.Bucket> bucketList = dateHistogram.getBuckets();
        for (Histogram.Bucket bucket : bucketList) {
            HashMap<String, Long> resultMap = new HashMap<>();
            resultMap.put(bucket.getKeyAsString(), bucket.getDocCount());
            list.add(resultMap);
        }
        ResultData resultData = new ResultData();
        resultData.setQueryTime(new Date());
        resultData.setData(list);
        resultData.setNumberFound(searchResponse.getHits().getTotalHits().value);
        resultData.setStart(content.getStart());
        return resultData;
    }

    @Override
    public ResultData dateHistogramAgg(QueryCommand content) throws Exception {
        SearchRequest searchRequest = buildSearchRequest(content.getIndexName());
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.matchAllQuery());
        searchSourceBuilder.trackTotalHits(true);
        // 聚集
        String dateField = content.getAggField();
        Integer step = content.getStep();
        if (step != null && dateField != null) {
            DateHistogramAggregationBuilder dateHistogramAggregationBuilder = AggregationBuilders
                    .dateHistogram("aggsName")
                    .field(dateField)
                    .fixedInterval(DateHistogramInterval.seconds(step))
                    // .extendedBounds(new ExtendedBounds("2020-09-01 00:00:00", "2020-09-02 05:00:00")
                    .minDocCount(0L);
            searchSourceBuilder.query(queryBuilder).aggregation(dateHistogramAggregationBuilder);
        } else {
            searchSourceBuilder.query(queryBuilder);
        }
        searchRequest.source(searchSourceBuilder);
        searchSourceBuilder.from(content.getStart());
        searchSourceBuilder.size(content.getRows());
        SearchResponse searchResponse =  restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        Aggregations jsonAgg = searchResponse.getAggregations();
        Histogram dateHistogram = jsonAgg.get("aggName");
        List<? extends Histogram.Bucket> bucketList = dateHistogram.getBuckets();
        List<BucketResult> list = new ArrayList<>();
        for (Histogram.Bucket bucket : bucketList) {
            BucketResult br = new BucketResult(bucket.getKeyAsString(), bucket.getDocCount());
            list.add(br);
        }
        ResultData resultData = new ResultData();
        resultData.setQueryTime(new Date());
        resultData.setData(list);
        resultData.setNumberFound(searchResponse.getHits().getTotalHits().value);
        resultData.setStart(content.getStart());
        return resultData;
    }

	@Override
	public ResultData nestedTermsAgg() throws Exception {
		SearchRequest searchRequest = buildSearchRequest("city");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.matchAllQuery());
        NestedAggregationBuilder aggregation = AggregationBuilders.nested("nestedAgg", "country")
        		.subAggregation(AggregationBuilders.terms("groupByCountry")
        						.field("country.countryName.keyword").size(100)
        						.order(BucketOrder.count(false)));
        searchSourceBuilder.query(queryBuilder).aggregation(aggregation);
        searchSourceBuilder.trackTotalHits(true);
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        Nested result = searchResponse.getAggregations().get("nestedAgg");
        Terms groupByCountry = result.getAggregations().get("groupByCountry");
        List<BucketResult> list = ToolUtils.getBuckets(groupByCountry);

        ResultData resultData = new ResultData();
        resultData.setQueryTime(new Date());
        resultData.setData(list.subList(0, 10));
        resultData.setNumberFound(searchResponse.getHits().getTotalHits().value);
        resultData.setStart(0);
        return resultData;
	}

    public static SearchRequest buildSearchRequest(String indexName) {
        return  new SearchRequest(indexName);
    }
}
