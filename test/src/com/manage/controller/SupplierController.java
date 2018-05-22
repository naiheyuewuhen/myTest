package com.manage.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.manage.model.GoodsSupplierInfo;
import com.manage.model.SupplierInfo;
import com.manage.model.WebPage;
import com.manage.service.SupplierService;

@Controller
@RequestMapping("/supplier")
public class SupplierController {
	@Resource
	private SupplierService supplierService;
	
	@ResponseBody
	@RequestMapping("/getById")
	public SupplierInfo getById(@RequestParam("id") Integer id){
		SupplierInfo supplierInfo=supplierService.getById(id,true);
		return supplierInfo;
	}
	@ResponseBody
	@RequestMapping("/getByIdWithNoUseGoods")
	public SupplierInfo getByIdWithNoUseGoods(@RequestParam("id") Integer id){
		SupplierInfo supplierInfo=supplierService.getById(id,false);
		return supplierInfo;
	}
	
	@ResponseBody
	@RequestMapping("/getAll")
	public List<SupplierInfo> getAll(@RequestParam Map<String, String> map){
		return supplierService.getAll(map,false).getRows();
	}
	@ResponseBody
	@RequestMapping("/getAllpage")
	public WebPage<SupplierInfo> getAllWithPage(@RequestParam Map<String, String> map){
		return supplierService.getAll(map,true);
	}
	@ResponseBody
	@RequestMapping("/getAllInIds")
	public List<SupplierInfo> getAllInIds(@RequestParam("ids") String ids){
		return supplierService.getAllInIds(ids);
	}
	@ResponseBody
	@RequestMapping("/getAllByContractId")
	public List<SupplierInfo> getAllByContractId(@RequestParam("contractId") Integer contractId,@RequestParam(required=false) Map<String, String> map){
		return supplierService.getAllInContractByContractId(contractId,map);
	}
	@ResponseBody
	@RequestMapping("/save")
	public String save(SupplierInfo supplierInfo,@RequestParam("goodsSupplierList") String goodsSupplierList) {
		Map<String,String> map=new HashMap<>();
		map.put("name", supplierInfo.getName());
		if(supplierInfo.getId()>0) {
			map.put("id", supplierInfo.getId().toString());
		}
		if(supplierService.getExist(map)) {
			return "{\"success\":false,\"data\":\"供应商已存在\"}";
		}
		Date date=new Date();
		Gson gson = new Gson();
		List<GoodsSupplierInfo> goodsSupplierInfoList = gson.fromJson(goodsSupplierList, new TypeToken<List<GoodsSupplierInfo>>(){}.getType());  
//		goodsInfo.setStatus(1);
		supplierInfo.setGoodsSupplierInfoList(goodsSupplierInfoList);
		supplierInfo.setCreateTime(date);
		supplierService.saveOrUpdate(supplierInfo);
		return "{\"success\":true,\"data\":\"ok\"}";
	}

}
