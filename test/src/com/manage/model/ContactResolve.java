package com.manage.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 技术拆解
 * @author liang
 *
 */
public class ContactResolve {
	
	private Integer id;
	private Integer contractId;
	private String contractCode;
	private String resolveName;
	private String createUser;
	private Date createTime;
	private String updateUser;
	private Date updateTime;
	private String remark;
	private Integer status;
	private List<ContractGoods> contractGoodsList;
	
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
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public String getResolveName() {
		return resolveName;
	}
	public void setResolveName(String resolveName) {
		this.resolveName = resolveName;
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
	public List<ContractGoods> getContractGoodsList() {
		return contractGoodsList;
	}
	public void setContractGoodsList(List<ContractGoods> contractGoodsList) {
		this.contractGoodsList = contractGoodsList;
	}

}
