package com.manage.model;

import java.util.List;

public class GoodsGroup {

	private Integer id;
	private String code;
	private String name;
	private String remark;
	private Integer status;
	private List<GoodsGroupDetail> goodsGroupDetailList;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public List<GoodsGroupDetail> getGoodsGroupDetailList() {
		return goodsGroupDetailList;
	}
	public void setGoodsGroupDetailList(List<GoodsGroupDetail> goodsGroupDetailList) {
		this.goodsGroupDetailList = goodsGroupDetailList;
	}
	
}
