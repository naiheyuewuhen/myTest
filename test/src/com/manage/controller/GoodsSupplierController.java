package com.manage.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.manage.model.GoodsSupplierInfo;
import com.manage.service.GoodsSupplierService;

@Controller
@RequestMapping("/goodsSupplier")
public class GoodsSupplierController {
	
	@Resource
	private GoodsSupplierService goodsSupplierService;
	
	@ResponseBody
	@RequestMapping("/getAllByGoodsId")
	public List<GoodsSupplierInfo> getAllByGoodsId(@RequestParam Map<String, String> map){
		return goodsSupplierService.getAllByGoodsId(map);
	}
//	public List<GoodsSupplierInfo> getAllByGoodsId(@RequestParam(name="goodsId",required=true) Integer goodsId){
//		return goodsSupplierService.getAllByGoodsId(goodsId);
//	}
	
	@ResponseBody
	@RequestMapping("/getAllBySupplierId")
	public List<GoodsSupplierInfo> getAllBySupplierId(@RequestParam(name="supplierId") Integer supplierId){
		return goodsSupplierService.getAllBySupplierId(supplierId);
	}

}
