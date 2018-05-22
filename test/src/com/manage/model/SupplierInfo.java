package com.manage.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 供应商
 * @author liang
 *
 */
public class SupplierInfo {
	private Integer id;
	private String name;
	private String phone;
	private String address;
	private Date createTime;
	/**
	 * 排序编号
	 */
	private Integer sortNum;
	private String remark;
	private Integer status;
//	private Integer[] goodsInfoIds;
	private List<GoodsSupplierInfo> goodsSupplierInfoList;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
//	public Integer[] getGoodsInfoIds() {
//		return goodsInfoIds;
//	}
//	public void setGoodsInfoIds(Integer[] goodsInfoIds) {
//		this.goodsInfoIds = goodsInfoIds;
//	}
	public List<GoodsSupplierInfo> getGoodsSupplierInfoList() {
		return goodsSupplierInfoList;
	}
	public void setGoodsSupplierInfoList(List<GoodsSupplierInfo> goodsSupplierInfoList) {
		this.goodsSupplierInfoList = goodsSupplierInfoList;
	}
}
