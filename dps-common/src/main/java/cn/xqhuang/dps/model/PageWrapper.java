package cn.xqhuang.dps.model;

import java.util.Objects;

public class PageWrapper<T> {
    private T result;
    private Integer pageSize;
    private Integer pageNo;
    private Integer totalCount;

    public PageWrapper() {}

    public PageWrapper(Integer pageNo, Integer pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public PageWrapper(Integer pageNo, Integer pageSize, Integer totalCount) {
        this(pageNo, pageSize);
        this.totalCount = totalCount;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
    	this.pageNo = pageNo;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public int getPageCount() {
        if (Objects.isNull(totalCount) || totalCount <= 0) {
            return 0;
        } else {
            return totalCount % pageSize == 0 ? totalCount / pageSize : (totalCount / pageSize) + 1;
        }
    }
}
