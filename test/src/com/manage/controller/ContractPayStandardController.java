package com.manage.controller;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.manage.model.ContractPayStandard;
import com.manage.model.WebPage;
import com.manage.service.ContractPayStandardService;

@Controller
@RequestMapping("/contractPayStandard")
public class ContractPayStandardController {
	
	@Resource
	private ContractPayStandardService contractPayStandardService;
	
	@ResponseBody
	@RequestMapping("/getById")
	public ContractPayStandard getById(@RequestParam("id") Integer id){
		return contractPayStandardService.getById(id);
	}
	
	@ResponseBody
	@RequestMapping("/getAll")
	public List<ContractPayStandard> getAll(@RequestParam Map<String, String> map){
		return contractPayStandardService.getAll(map,false).getRows();
	}
	
	@ResponseBody
	@RequestMapping("/getAllpage")
	public WebPage<ContractPayStandard> getAllWithPage(@RequestParam Map<String, String> map){
		return contractPayStandardService.getAll(map,true);
	}
	@ResponseBody
	@RequestMapping(value="/save", produces = "application/json; charset=utf-8")
	public String save(ContractPayStandard model) {
		Map<String,String> map=new HashMap<>();
		map.put("startDate", new SimpleDateFormat("yyyy-MM-dd").format(model.getStartDate()));
		map.put("endDate", new SimpleDateFormat("yyyy-MM-dd").format(model.getEndDate()));
		if(model.getId()>0) {
			map.put("id", model.getId().toString());
		}
		if(contractPayStandardService.getExist(map)) {
			return "{\"success\":false,\"msg\":\"时间有重叠\"}";
		}
		contractPayStandardService.saveOrUpdate(model);
		return "{\"success\":true,\"msg\":\"ok\"}";
	}

}
