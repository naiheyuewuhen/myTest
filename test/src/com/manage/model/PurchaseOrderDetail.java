package com.manage.model;

import java.math.BigDecimal;

/**
 * 合同包含的的原材料
 * @author liang
 *
 */
public class PurchaseOrderDetail {
	private Integer id;
	private Integer purchaseOrderId;
	private Integer contractId;
	private Integer goodsId;
	private String goodsCode;
	private String goodsName;
	private String goodsStandard;
	private Integer goodsNum;
	private Integer goodsTotalNum;
	private Integer goodsSumNum;
	private BigDecimal price;
	private String supplierId;
	private String supplierName;
	private Integer status;
	public Integer getGoodsSumNum() {
		return goodsSumNum;
	}
	public void setGoodsSumNum(Integer goodsSumNum) {
		this.goodsSumNum = goodsSumNum;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPurchaseOrderId() {
		return purchaseOrderId;
	}
	public void setPurchaseOrderId(Integer purchaseOrderId) {
		this.purchaseOrderId = purchaseOrderId;
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
	public Integer getGoodsTotalNum() {
		return goodsTotalNum;
	}
	public void setGoodsTotalNum(Integer goodsTotalNum) {
		this.goodsTotalNum = goodsTotalNum;
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
	public String getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}
