package cn.xqhuang.dps.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DataTable<T> {
	// 当前记录总数
	Long recordsFiltered;
	// 页面大小
	Integer length;
	// guid
	String draw;
	// 总记录数
	Long recordsTotal;
	// 记录数据
	List<T> data;
}
