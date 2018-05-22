package com.manage.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class GoodsInfo {
	private Integer id;
	/**
	 * 原材料编码（编号）
	 */
	private String code;
	/**
	 * 原材料名称
	 */
	private String name;
	/**
	 * 规格
	 */
	private String standard;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 排序编号
	 */
	private Integer sortNum;
	private String remark;
	/**
	 * 状态（0：无效；1：有效）
	 */
	private Integer status;
//	private Integer[] supplierInfoIds;
	private List<GoodsSupplierInfo> goodsSupplierInfoList;
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
	public String getStandard() {
		return standard;
	}
	public void setStandard(String standard) {
		this.standard = standard;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getSortNum() {
		return sortNum;
	}
	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
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
//	public Integer[] getSupplierInfoIds() {
//		return supplierInfoIds;
//	}
//	public void setSupplierInfoIds(Integer[] supplierInfoIds) {
//		this.supplierInfoIds = supplierInfoIds;
//	}
	public List<GoodsSupplierInfo> getGoodsSupplierInfoList() {
		return goodsSupplierInfoList;
	}
	public void setGoodsSupplierInfoList(List<GoodsSupplierInfo> goodsSupplierInfoList) {
		this.goodsSupplierInfoList = goodsSupplierInfoList;
	}
	
}
