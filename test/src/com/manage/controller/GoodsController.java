package com.manage.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.manage.model.GoodsInfo;
import com.manage.model.WebPage;
import com.manage.service.GoodsService;

@Controller
@RequestMapping("/goods")
public class GoodsController {
	
	@Resource
	private GoodsService goodsService;
	
	@ResponseBody
	@RequestMapping("/getById")
	public GoodsInfo getById(@RequestParam("id") Integer id){
		return goodsService.getById(id);
	}
	
	@ResponseBody
	@RequestMapping("/getAll")
	public List<GoodsInfo> getAll(@RequestParam Map<String, String> map){
		return goodsService.getAll(map,false).getRows();
	}
	@ResponseBody
	@RequestMapping("/autocomplete")
	public List<GoodsInfo> getAll(@RequestParam(defaultValue="") String q){
		if(q.equals("")) {
			return new ArrayList<>();
		}else {
			Map<String,String> map=new HashMap<>();
			map.put("code", q);
			map.put("status", "1");
			return goodsService.getAll(map,false).getRows();
		}
	}
	@ResponseBody
	@RequestMapping("/getAllpage")
	public WebPage<GoodsInfo> getAllWithPage(@RequestParam Map<String, String> map){
		return goodsService.getAll(map,true);
	}
	@ResponseBody
	@RequestMapping(value="/save", produces = "application/json; charset=utf-8")
	public String save(GoodsInfo goodsInfo) {
		Map<String,String> map=new HashMap<>();
		map.put("code", goodsInfo.getCode());
		if(goodsInfo.getId()>0) {
			map.put("id", goodsInfo.getId().toString());
		}
		if(goodsService.getExist(map)) {
			return "{\"success\":false,\"data\":\"编号已存在\"}";
		}
		Date date=new Date();
//		goodsInfo.setStatus(1);
		goodsInfo.setCreateTime(date);
		goodsService.saveOrUpdate(goodsInfo);
		return "{\"success\":true,\"data\":\"ok\"}";
	}

}
