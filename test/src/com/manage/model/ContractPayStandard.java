package com.manage.model;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ContractPayStandard {

	private Integer id;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date startDate;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date endDate;
	private BigDecimal payNo1;
	private BigDecimal payNo2;
	private BigDecimal payNo3;
	private BigDecimal payNo4;
	private BigDecimal payNo5;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	@JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public BigDecimal getPayNo1() {
		return payNo1;
	}
	public void setPayNo1(BigDecimal payNo1) {
		this.payNo1 = payNo1;
	}
	public BigDecimal getPayNo2() {
		return payNo2;
	}
	public void setPayNo2(BigDecimal payNo2) {
		this.payNo2 = payNo2;
	}
	public BigDecimal getPayNo3() {
		return payNo3;
	}
	public void setPayNo3(BigDecimal payNo3) {
		this.payNo3 = payNo3;
	}
	public BigDecimal getPayNo4() {
		return payNo4;
	}
	public void setPayNo4(BigDecimal payNo4) {
		this.payNo4 = payNo4;
	}
	public BigDecimal getPayNo5() {
		return payNo5;
	}
	public void setPayNo5(BigDecimal payNo5) {
		this.payNo5 = payNo5;
	}
}
