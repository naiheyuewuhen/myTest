package com.manage.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.stereotype.Service;

import com.manage.dao.ContractDao;
import com.manage.dao.ContractFileDao;
import com.manage.dao.ContractPayStandardDao;
import com.manage.dao.PaymentDao;
import com.manage.model.ContractFile;
import com.manage.model.ContractInfo;
import com.manage.model.ContractPayStandard;
import com.manage.model.Payment;
import com.manage.model.WebPage;
import com.manage.service.ContractService;

@Service("contractService")
public class ContractServiceImpl implements ContractService {

	@Resource
	private ContractDao contractDao;
	@Resource
	private PaymentDao paymentDao;
	@Resource
	private ContractFileDao contractFileDao;
	@Resource
	private ContractPayStandardDao contractPayStandardDao;
	
	@Override
	public Boolean saveOrUpdate(ContractInfo contractInfo,List<Payment> paymentAgreedList,List<ContractFile> contractFileList) {
		//更新操作
		Integer contractId = null;
		List<Payment> paymentListUpdate=new ArrayList<Payment>();
		List<Payment> paymentListInsert=new ArrayList<Payment>();
		List<ContractFile> contractFileListUpdate=new ArrayList<ContractFile>();
		List<ContractFile> contractFileListInsert=new ArrayList<ContractFile>();
		paymentAgreedList.sort(null);//按照约定支付日期的升序排序
		ContractPayStandard contractPayStandard = contractPayStandardDao.getByNow(new Date());
		contractInfo.setContractType(1);//默认为标准合同，只需要一级审核
		if(contractPayStandard==null||paymentAgreedList.size()==0) {
			contractInfo.setContractType(0);//非标准合同，需要两级审批
		}else {
			BigDecimal paymentTotal=new BigDecimal("0");
			for(int i=0;i<paymentAgreedList.size();i++) {
				paymentTotal=paymentTotal.add(paymentAgreedList.get(i).getAmount());
				if(this.isPayStandard(contractPayStandard,contractInfo.getTotalAmount(),paymentTotal,i)) {
					contractInfo.setContractType(0);//非标准合同，需要两级审批
					break;
				}
			}
		}
		if(contractInfo.getId()>0) {
			contractId=contractInfo.getId();
			contractDao.update(contractInfo);
		}else {
			contractId=contractDao.save(contractInfo);
		}
		for(Payment payment : paymentAgreedList) {
			payment.setContractId(contractId);
			if(payment.getId()>0) {
				paymentListUpdate.add(payment);
			}else {
				paymentListInsert.add(payment);
			}
		}
		for(ContractFile contractFile : contractFileList) {
			contractFile.setContractId(contractId);
			if(contractFile.getId()>0) {
				contractFileListUpdate.add(contractFile);
			}else {
				contractFileListInsert.add(contractFile);
			}
		}
		//先更新所关联的约定付款方式记录为无效，后续根据条件更新为有效或插入新的记录
		paymentDao.updateAgreedUnuse(paymentAgreedList.get(0));
		//先更新所关联的合同附件为无效，后续根据条件更新为有效或插入新的记录
		contractFileDao.updateUnuse(contractId);
		
		paymentDao.updateAgreedBatch(paymentListUpdate);
		paymentDao.saveAgreedBatch(paymentListInsert);
		
		contractFileDao.updateBatch(contractFileListUpdate);
		contractFileDao.saveBatch(contractFileListInsert);
		return true;
	}

	@Override
	public WebPage<ContractInfo> getAll(Map<String, String> map,Boolean hasPage) {
		return contractDao.getAll(map,hasPage);
	}
	@Override
	public ContractInfo getById(Integer id) {
		Map<String,String> map=new HashMap<String,String>();
		map.put("contractId", id.toString());
		map.put("status", "1");
		map.put("sort", "createTime");
		map.put("order", "asc");
		ContractInfo contractInfo = contractDao.getById(id);
		List<ContractFile> contractFileList = contractFileDao.getAll(map,false).getRows();
		List<Payment> paymentAgreedList = paymentDao.getAgreedAll(map,false).getRows();
		List<Payment> paymentRealityList = paymentDao.getRealityAll(map,false).getRows();
		contractInfo.setContractFileList(contractFileList);
		contractInfo.setPaymentAgreedList(paymentAgreedList);
		contractInfo.setPaymentRealityList(paymentRealityList);
		return contractInfo;
	}
	@Override
	public ContractInfo getOnlyWithFilesById(Integer id) {
		Map<String,String> map=new HashMap<String,String>();
		map.put("contractId", id.toString());
		map.put("status", "1");
		map.put("sort", "createTime");
		map.put("order", "asc");
		ContractInfo contractInfo = contractDao.getById(id);
		List<ContractFile> contractFileList = contractFileDao.getAll(map,false).getRows();
		contractInfo.setContractFileList(contractFileList);
		return contractInfo;
	}
	@Override
	public ContractInfo getInfoById(Integer id) {
		ContractInfo contractInfo = contractDao.getById(id);
		return contractInfo;
	}

	@Override
	public Boolean getExist(Map<String, String> map) {
		return contractDao.getExist(map);
	}
	
	@Override
	public Boolean updateAuditing(ContractInfo contractInfo) {
		contractDao.updateAuditing(contractInfo);
		return true;
	}
	@Override
	public Boolean updateStatus(Integer contractInfoId,Integer status) {
		contractDao.updateStatus(contractInfoId,status);
		return true;
	}
	private Boolean isPayStandard(ContractPayStandard contractPayStandard,BigDecimal totalAmount,BigDecimal amount,Integer num) {
		switch (num) {
		case 0:
			return totalAmount.multiply(contractPayStandard.getPayNo1()).multiply(new BigDecimal("0.01")).compareTo(amount)==1;
		case 1:
			return totalAmount.multiply(contractPayStandard.getPayNo1().add(contractPayStandard.getPayNo2())).multiply(new BigDecimal("0.01")).compareTo(amount)==1;
		case 2:
			return totalAmount.multiply(contractPayStandard.getPayNo1().add(contractPayStandard.getPayNo2()).add(contractPayStandard.getPayNo3())).multiply(new BigDecimal("0.01")).compareTo(amount)==1;
		case 3:
			return totalAmount.multiply(contractPayStandard.getPayNo1().add(contractPayStandard.getPayNo2()).add(contractPayStandard.getPayNo3()).add(contractPayStandard.getPayNo4())).multiply(new BigDecimal("0.01")).compareTo(amount)==1;
		case 4:
			return totalAmount.multiply(contractPayStandard.getPayNo1().add(contractPayStandard.getPayNo2()).add(contractPayStandard.getPayNo3()).add(contractPayStandard.getPayNo4()).add(contractPayStandard.getPayNo5())).multiply(new BigDecimal("0.01")).compareTo(amount)==1;
		default:
			return false;
		}
	}

	@Test
	public void test() throws ParseException {
		Date date1=new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-21");
		Date date2=new SimpleDateFormat("yyyy-MM-dd").parse("2018-07-21");
		Date date3=new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-02");
		Date date4=new SimpleDateFormat("yyyy-MM-dd").parse("2018-10-21");
		List<Payment> treeSet=new ArrayList<Payment>();
		Payment payment1=new Payment();
		payment1.setPaymentDate(date1);
		payment1.setPaymentMode("现金");
		payment1.setAmount(new BigDecimal("10"));
		treeSet.add(payment1);
		Payment payment2=new Payment();
		payment2.setPaymentDate(date2);
		payment2.setPaymentMode("支票");
		payment2.setAmount(new BigDecimal("20"));
		treeSet.add(payment2);
		Payment payment3=new Payment();
		payment3.setPaymentDate(date3);
		payment3.setPaymentMode("转账");
		payment3.setAmount(new BigDecimal("30"));
		treeSet.add(payment3);
		Payment payment4=new Payment();
		payment4.setPaymentDate(date4);
		payment4.setPaymentMode("刷卡");
		payment4.setAmount(new BigDecimal("40"));
		treeSet.add(payment4);
		treeSet.sort(null);
		System.out.println(treeSet.toString());
	}
}
