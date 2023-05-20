package cn.xqhuang.dps.controller;

import cn.xqhuang.dps.model.QueryCommand;
import cn.xqhuang.dps.model.RangeQuery;
import cn.xqhuang.dps.model.ResultData;
import cn.xqhuang.dps.service.AggService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.annotation.Resource;

@Api(tags = "聚集统计接口")
@RestController
public class AggController {
	@Resource
	private AggService aggService;
	
	/**
	 * terms聚集接口
	 * @param: content
	 * @return:
	 * @throws: Exception
	 */
    @ApiOperation("词条聚集")
	@RequestMapping(value = "/termsAgg", method = RequestMethod.POST)
    public ResultData termsAgg(@RequestBody QueryCommand query) throws Exception{
		return aggService.termsAgg(query);
	}
    
	/**
	 * 范围聚集接口
	 * @param: content
	 * @return:
	 * @throws: Exception
	 */
    @ApiOperation("范围聚集")
	@RequestMapping(value = "/rangeAggs", method = RequestMethod.POST)
    public ResultData rangeAgg(@RequestBody RangeQuery content) throws Exception{
		return aggService.rangeAgg(content);
	}
	
	/**
	 * histogram聚集接口
	 * @param: content
	 * @return:
	 * @throws: Exception
	 */
    @ApiOperation("直方图聚集")
	@RequestMapping(value = "/histogramAggs", method = RequestMethod.POST)
    public ResultData histogramAgg(@RequestBody QueryCommand query) throws Exception{
		return aggService.histogramAgg(query);
	}
	
	/**
	 * dateHistogram聚集接口
	 * @param: content
	 * @return:
	 * @throws: Exception
	 */
    @ApiOperation("日期直方图聚集")
	@RequestMapping(value = "/dateHistogramAgg", method = RequestMethod.POST)
    public ResultData dateHistogramAgg(@RequestBody QueryCommand query) throws Exception{
		return aggService.dateHistogramAgg(query);
	}
    
    @ApiOperation("按国家嵌套对象词条聚集")
	@RequestMapping(value = "/nestedTermsAgg", method = RequestMethod.GET)
    public ResultData nestedTermsAgg() throws Exception{
		return aggService.nestedTermsAgg();
	}
    
	@RequestMapping(value = "/analysis", method = RequestMethod.GET)
    public String analysis() {
		return "analysis";
	}    
}
