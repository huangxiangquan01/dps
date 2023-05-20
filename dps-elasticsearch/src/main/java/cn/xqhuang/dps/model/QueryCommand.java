package cn.xqhuang.dps.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Description:检索参数
 */
@Getter
@Setter
public class QueryCommand {
	// 表名
	private String indexName;
	//关键词
	private String keyWords;
	//搜索域
	private String search_field;
	//逻辑连接词
	private String operator;
	// 排序
	private String sort;
	//起始位置
	private int start;
	//返回条数
	private int rows;
	//返回字段
	private String returnFiled;
	
	private String startDate;

	private String endDate;
	// 聚集字段
	private String aggField;
	// 步长
	private Integer step;
	// 滚动分页id
	private String scrollId;
}
