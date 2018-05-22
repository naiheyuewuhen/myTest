package com.manage.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ContractInfo {
	
	private Integer id;
	/**
	 * 本补充合同所依附的主合同id（0：表示本合同不是补充合同，即是“主合同”）
	 */
	private Integer fatherId;
	/**
	 * 合同编号
	 */
	private String code;
	/**
	 * 甲方（单位）
	 */
	private String firstParty;
	/**
	 * 甲方地址
	 */
	private String firstAddress;
	/**
	 * 甲方联系人
	 */
	private String firstLinkman;
	/**
	 * 甲方联系电话
	 */
	private String firstPhone;
	/**
	 * 乙方（单位）
	 */
	private String secondParty;
	/**
	 * 乙方地址
	 */
	private String secondAddress;
	/**
	 * 乙方联系人
	 */
	private String secondLinkman;
	/**
	 * 乙方联系电话
	 */
	private String secondPhone;
	/**
	 * 签约时间
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date contractDate;
	/**
	 * 创建者
	 */
	private String createUser;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 最后修改者
	 */
	private String updateUser;
	/**
	 * 最后修改时间
	 */
	private Date updateTime;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 合同状态（0：待审核；1审核通过；2：审核未通过；3：完成；4：合同取消）
	 */
	private Integer status;
	/**
	 * 约定支付方式
	 */
	private List<Payment> paymentAgreedList;
	/**
	 * 实际付款方式
	 */
	private List<Payment> paymentRealityList;
	/**
	 * 合同文本附件
	 */
	private List<ContractFile> contractFileList;
	
	private String auditor;
	private Date auditingTime;
	private String auditingRemark;
	private BigDecimal totalAmount;
	private Integer contractType;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getFatherId() {
		return fatherId;
	}
	public void setFatherId(Integer fatherId) {
		this.fatherId = fatherId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getFirstParty() {
		return firstParty;
	}
	public void setFirstParty(String firstParty) {
		this.firstParty = firstParty;
	}
	public String getFirstAddress() {
		return firstAddress;
	}
	public void setFirstAddress(String firstAddress) {
		this.firstAddress = firstAddress;
	}
	public String getFirstLinkman() {
		return firstLinkman;
	}
	public void setFirstLinkman(String firstLinkman) {
		this.firstLinkman = firstLinkman;
	}
	public String getFirstPhone() {
		return firstPhone;
	}
	public void setFirstPhone(String firstPhone) {
		this.firstPhone = firstPhone;
	}
	public String getSecondParty() {
		return secondParty;
	}
	public void setSecondParty(String secondParty) {
		this.secondParty = secondParty;
	}
	public String getSecondAddress() {
		return secondAddress;
	}
	public void setSecondAddress(String secondAddress) {
		this.secondAddress = secondAddress;
	}
	public String getSecondLinkman() {
		return secondLinkman;
	}
	public void setSecondLinkman(String secondLinkman) {
		this.secondLinkman = secondLinkman;
	}
	public String getSecondPhone() {
		return secondPhone;
	}
	public void setSecondPhone(String secondPhone) {
		this.secondPhone = secondPhone;
	}
//	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
	public Date getContractDate() {
		return contractDate;
	}
	public void setContractDate(Date contractDate) {
		this.contractDate = contractDate;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
//	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
//	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
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
	public List<Payment> getPaymentAgreedList() {
		return paymentAgreedList;
	}
	public void setPaymentAgreedList(List<Payment> paymentAgreedList) {
		this.paymentAgreedList = paymentAgreedList;
	}
	public List<Payment> getPaymentRealityList() {
		return paymentRealityList;
	}
	public void setPaymentRealityList(List<Payment> paymentRealityList) {
		this.paymentRealityList = paymentRealityList;
	}
	public List<ContractFile> getContractFileList() {
		return contractFileList;
	}
	public void setContractFileList(List<ContractFile> contractFileList) {
		this.contractFileList = contractFileList;
	}
	public String getAuditor() {
		return auditor;
	}
	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	public Date getAuditingTime() {
		return auditingTime;
	}
	public void setAuditingTime(Date auditingTime) {
		this.auditingTime = auditingTime;
	}
	public String getAuditingRemark() {
		return auditingRemark;
	}
	public void setAuditingRemark(String auditingRemark) {
		this.auditingRemark = auditingRemark;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public Integer getContractType() {
		return contractType;
	}
	public void setContractType(Integer contractType) {
		this.contractType = contractType;
	}
	
}
