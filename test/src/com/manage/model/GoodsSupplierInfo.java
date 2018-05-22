package com.manage.model;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class GoodsSupplierInfo {
	
	private Integer id;
	private Integer goodsId;
	private Integer supplierId;
	private BigDecimal price;
	private Integer status;
	
	private String goodsCode;
	private String goodsName;
	private String goodsRemark;
	private Integer goodsSortNum;
	private Integer goodsStatus;
	private Date goodsCreateTime;
	private String goodsStandard;
	
	private String supplierName;
	private String supplierPhone;
	private String supplierAddress;
	private String supplierRemark;
	private Integer supplierSortNum;
	private Integer supplierStatus;
	private Date supplierCreateTime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}
	public Integer getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getGoodsCode() {
		return goodsCode;
	}
	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getGoodsRemark() {
		return goodsRemark;
	}
	public void setGoodsRemark(String goodsRemark) {
		this.goodsRemark = goodsRemark;
	}
	public Integer getGoodsSortNum() {
		return goodsSortNum;
	}
	public void setGoodsSortNum(Integer goodsSortNum) {
		this.goodsSortNum = goodsSortNum;
	}
	public Integer getGoodsStatus() {
		return goodsStatus;
	}
	public void setGoodsStatus(Integer goodsStatus) {
		this.goodsStatus = goodsStatus;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	public Date getGoodsCreateTime() {
		return goodsCreateTime;
	}
	public void setGoodsCreateTime(Date goodsCreateTime) {
		this.goodsCreateTime = goodsCreateTime;
	}
	public String getGoodsStandard() {
		return goodsStandard;
	}
	public void setGoodsStandard(String goodsStandard) {
		this.goodsStandard = goodsStandard;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getSupplierPhone() {
		return supplierPhone;
	}
	public void setSupplierPhone(String supplierPhone) {
		this.supplierPhone = supplierPhone;
	}
	public String getSupplierAddress() {
		return supplierAddress;
	}
	public void setSupplierAddress(String supplierAddress) {
		this.supplierAddress = supplierAddress;
	}
	public String getSupplierRemark() {
		return supplierRemark;
	}
	public void setSupplierRemark(String supplierRemark) {
		this.supplierRemark = supplierRemark;
	}
	public Integer getSupplierSortNum() {
		return supplierSortNum;
	}
	public void setSupplierSortNum(Integer supplierSortNum) {
		this.supplierSortNum = supplierSortNum;
	}
	public Integer getSupplierStatus() {
		return supplierStatus;
	}
	public void setSupplierStatus(Integer supplierStatus) {
		this.supplierStatus = supplierStatus;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	public Date getSupplierCreateTime() {
		return supplierCreateTime;
	}
	public void setSupplierCreateTime(Date supplierCreateTime) {
		this.supplierCreateTime = supplierCreateTime;
	}
	
	
	
}
