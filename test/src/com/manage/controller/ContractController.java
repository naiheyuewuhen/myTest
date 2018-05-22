package com.manage.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.manage.model.ContractFile;
import com.manage.model.ContractInfo;
import com.manage.model.Payment;
import com.manage.model.WebPage;
import com.manage.service.ContractService;
import com.manage.util.JsonResult;
import com.manage.util.StringUtil;

@RequestMapping("/contract")
@Controller
public class ContractController {
	
	@Resource
	private ContractService contractService;

	@ResponseBody
	@RequestMapping("/getById")
	public ContractInfo getById(@RequestParam("id") Integer id){
		return contractService.getById(id);
	}
	@ResponseBody
	@RequestMapping("/getWithFilesById")
	public ContractInfo getWithFilesById(@RequestParam("id") Integer id){
		return contractService.getOnlyWithFilesById(id);
	}
	
	@ResponseBody
	@RequestMapping("/getAll")
	public List<ContractInfo> getAll(@RequestParam Map<String, String> map){
		return contractService.getAll(map,false).getRows();
	}
	@ResponseBody
	@RequestMapping("/getAllpage")
	public WebPage<ContractInfo> getAllWithPage(@RequestParam Map<String, String> map){
		return contractService.getAll(map,true);
	}
	@ResponseBody
	@RequestMapping(value="/save", produces = "application/json; charset=utf-8")
	public String save(ContractInfo contractInfo,@RequestParam("payments") String payments,@RequestParam("upfiles") String upfiles,HttpSession session) {
		Map<String,String> map=new HashMap<>();
		map.put("code", contractInfo.getCode());
		if(contractInfo.getId()>0) {
			map.put("id", contractInfo.getId().toString());
		}
		if(contractService.getExist(map)) {
			return "{\"success\":false,\"data\":\"编号已存在\"}";
		}
		Date date=new Date();
		Gson gson = new Gson();
		List<Payment> paymentList = gson.fromJson(payments, new TypeToken<List<Payment>>(){}.getType());  
		List<ContractFile> contractFileList = gson.fromJson(upfiles, new TypeToken<List<ContractFile>>(){}.getType());  
//		contractInfo.setStatus(1);
		contractInfo.setCreateUser((String)session.getAttribute("userName"));
		contractInfo.setCreateTime(date);
		contractInfo.setUpdateUser((String)session.getAttribute("userName"));
		contractInfo.setUpdateTime(date);
		for(ContractFile contractFile : contractFileList) {
			contractFile.setStatus(1);
		}
		for(Payment payment : paymentList) {
			payment.setStatus(1);
			payment.setCreateUser((String)session.getAttribute("userName"));
			payment.setCreateTime(date);
			payment.setUpdateUser((String)session.getAttribute("userName"));
			payment.setUpdateTime(date);
		}
		contractService.saveOrUpdate(contractInfo,paymentList,contractFileList);
		return "{\"success\":true,\"data\":\"ok\"}";
	}
	
	@ResponseBody
	@RequestMapping(value="/auditing", produces = "application/json; charset=utf-8")
	public String auditing(ContractInfo contractInfo,HttpSession session) {
		Date date=new Date();
//		contractInfo.setStatus(1);
		contractInfo.setAuditor((String)session.getAttribute("userName"));
		contractInfo.setAuditingTime(date);
		contractService.updateAuditing(contractInfo);
		return "{\"success\":true,\"data\":\"ok\"}";
	}
	@ResponseBody
	@RequestMapping("/test")
	public String test() {
		List<Payment> paymentList = new ArrayList<Payment>();
		Payment payment =new Payment();
		payment.setId(1);
		payment.setAmount(BigDecimal.valueOf(51));
		payment.setContractId(11);
		payment.setPaymentMode("update11");
		Payment payment2 =new Payment();
		payment2.setId(2);
		payment2.setAmount(BigDecimal.valueOf(52));
		payment2.setContractId(12);
		payment2.setPaymentMode("update12");
		Payment payment3 =new Payment();
		payment3.setId(3);
		payment3.setAmount(BigDecimal.valueOf(53));
		payment3.setContractId(13);
		payment3.setPaymentMode("update13");
		paymentList.add(payment);
		paymentList.add(payment2);
		paymentList.add(payment3);
		int[] ids=JsonResult.updateBatchTest(paymentList);
		String result="success";
		try {
			result=JsonResult.object2Json(ids);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("yyyyyyyyyyyy");
		return result;
	}
	@Test
	public void test1() {
//		String noStr="username";
		String str= "userNameFirst";
		String field= "user_name_first";
		System.out.println(StringUtil.propertyToField(str));
		System.out.println(StringUtil.fieldToProperty(field));
		System.out.println(StringUtil.propertyToField(field));
		System.out.println(StringUtil.fieldToProperty(str));
	}
}
