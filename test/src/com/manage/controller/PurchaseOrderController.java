package com.manage.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.manage.model.PurchaseOrder;
import com.manage.service.PurchaseOrdersService;

@Controller
@RequestMapping("/purchaseOrder")
public class PurchaseOrderController {
	
	@Resource
	private PurchaseOrdersService purchaseOrdersService;
	
	@ResponseBody
	@RequestMapping("/getById")
	public PurchaseOrder getById(@RequestParam("id") Integer id){
		return purchaseOrdersService.getById(id);
	}
	
	@ResponseBody
	@RequestMapping("/getAll")
	public List<PurchaseOrder> getAll(@RequestParam Map<String, String> map){
		return purchaseOrdersService.getAll(map,false).getRows();
	}

}
