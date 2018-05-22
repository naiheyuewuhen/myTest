package com.manage.model;

import java.util.List;

public class WebPage<T> {

	/**
	 * 当前页
	 */
	private Integer page=1;
	/**
	 * 总记录数
	 */
	private Long total = 0L;

	/**
	 * 列表内容
	 */
	private List<T> rows;
	
	public WebPage(Long total,Integer page) {
		total=total==null?0:total;
		page=page==null?1:page;
		this.setTotal(total);
		this.setPage(page);
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}
}
