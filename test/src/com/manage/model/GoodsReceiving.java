package com.manage.model;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 原材料收货
 * @author liang
 *
 */
public class GoodsReceiving {
	private Integer id;
	private Integer contractId;
	private String contractCode;
	private Integer goodsId;
	private String goodsCode;
	private String goodsName;
	private String goodsStandard;
	private Integer goodsNum;
	private Integer goodsTotalNum;
	private Integer goodsSumNum;
	private BigDecimal price;
	private Integer supplierId;
	private String supplierName;
	private String consignee;
	private Date receptionTime;
	private String createUser;
	private Date createTime;
	private String remark;
	private Integer status;
	private Integer purchaseOrderId;
	
	public Integer getPurchaseOrderId() {
		return purchaseOrderId;
	}
	public void setPurchaseOrderId(Integer purchaseOrderId) {
		this.purchaseOrderId = purchaseOrderId;
	}
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
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public Integer getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getConsignee() {
		return consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	public Date getReceptionTime() {
		return receptionTime;
	}
	public void setReceptionTime(Date receptionTime) {
		this.receptionTime = receptionTime;
	}
	public Integer getGoodsTotalNum() {
		return goodsTotalNum;
	}
	public void setGoodsTotalNum(Integer goodsTotalNum) {
		this.goodsTotalNum = goodsTotalNum;
	}
	public Integer getGoodsSumNum() {
		return goodsSumNum;
	}
	public void setGoodsSumNum(Integer goodsSumNum) {
		this.goodsSumNum = goodsSumNum;
	}
}
