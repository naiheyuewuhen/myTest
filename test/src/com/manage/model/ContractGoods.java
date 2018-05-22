package com.manage.model;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 合同包含的的原材料
 * @author liang
 *
 */
public class ContractGoods {
	private Integer id;
	private Integer contractId;
	private Integer resolveId;
	private Integer goodsId;
	private String goodsCode;
	private String goodsName;
	private String goodsStandard;
	private Integer goodsNum;
	private BigDecimal price;
//	private BigDecimal priceTotal;
	private String supplierIdsAdvise;
	private String supplierNamesAdvise;
	private Integer supplierIdReality;
	private String supplierNameReality;
	private String createUser;
	private Date createTime;
	private String updateUser;
	private Date updateTime;
	private String realityUser;
	private Date realityTime;
	private String remark;
//	private Integer complete;
	private Integer status;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getContractId() {
		return contractId;
	}
	public void setContractId(Integer contractId) {
		this.contractId = contractId;
	}
	public Integer getResolveId() {
		return resolveId;
	}
	public void setResolveId(Integer resolveId) {
		this.resolveId = resolveId;
	}
	public Integer getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
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
	public String getGoodsStandard() {
		return goodsStandard;
	}
	public void setGoodsStandard(String goodsStandard) {
		this.goodsStandard = goodsStandard;
	}
	public Integer getGoodsNum() {
		return goodsNum;
	}
	public void setGoodsNum(Integer goodsNum) {
		this.goodsNum = goodsNum;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
//	public BigDecimal getPriceTotal() {
//		return priceTotal;
//	}
//	public void setPriceTotal(BigDecimal priceTotal) {
//		this.priceTotal = priceTotal;
//	}
	public String getSupplierIdsAdvise() {
		return supplierIdsAdvise;
	}
	public void setSupplierIdsAdvise(String supplierIdsAdvise) {
		this.supplierIdsAdvise = supplierIdsAdvise;
	}
	public String getSupplierNamesAdvise() {
		return supplierNamesAdvise;
	}
	public void setSupplierNamesAdvise(String supplierNamesAdvise) {
		this.supplierNamesAdvise = supplierNamesAdvise;
	}
	public Integer getSupplierIdReality() {
		return supplierIdReality;
	}
	public void setSupplierIdReality(Integer supplierIdReality) {
		this.supplierIdReality = supplierIdReality;
	}
	public String getSupplierNameReality() {
		return supplierNameReality;
	}
	public void setSupplierNameReality(String supplierNameReality) {
		this.supplierNameReality = supplierNameReality;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getRealityUser() {
		return realityUser;
	}
	public void setRealityUser(String realityUser) {
		this.realityUser = realityUser;
	}
	public Date getRealityTime() {
		return realityTime;
	}
	public void setRealityTime(Date realityTime) {
		this.realityTime = realityTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
//	public Integer getComplete() {
//		return complete;
//	}
//	public void setComplete(Integer complete) {
//		this.complete = complete;
//	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}
