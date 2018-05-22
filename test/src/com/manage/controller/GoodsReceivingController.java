package com.manage.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.manage.model.GoodsReceiving;
import com.manage.model.WebPage;
import com.manage.service.GoodsReceivingService;

@RequestMapping("/goodsreceiving")
@Controller
public class GoodsReceivingController {
	
	@Resource
	private GoodsReceivingService goodsReceivingService;

	@ResponseBody
	@RequestMapping(value="/getById")
	public GoodsReceiving getById(@RequestParam("id") Integer id){
		return goodsReceivingService.getById(id);
	}
	@ResponseBody
	@RequestMapping(value="/getAll")
	public List<GoodsReceiving> getAll(@RequestParam Map<String, Object> map){
		return goodsReceivingService.getAll(map,false).getRows();
	}
	@ResponseBody
	@RequestMapping(value="/getAllpage")
	public WebPage<GoodsReceiving> getAllWithPage(@RequestParam Map<String, Object> map){
		return goodsReceivingService.getAll(map,true);
	}
	@ResponseBody
	@RequestMapping(value="/getAllForContract")
	public List<GoodsReceiving> getAllForContract(@RequestParam Map<String, Object> map){
		return goodsReceivingService.getAllForContract(map);
	}
	@ResponseBody
	@RequestMapping(value="/save", produces = "application/json; charset=utf-8")
	public String save(@RequestParam String goodsReceivings,HttpSession session) {
		Date date=new Date();
		ObjectMapper mapper = new ObjectMapper();
		List<GoodsReceiving> goodsReceivingList;
		try {
			goodsReceivingList = mapper.readValue(goodsReceivings, new TypeReference<List<GoodsReceiving>>(){});
			for(GoodsReceiving GoodsReceiving : goodsReceivingList) {
				GoodsReceiving.setStatus(1);
				GoodsReceiving.setCreateUser((String)session.getAttribute("userName"));
				GoodsReceiving.setCreateTime(date);
			}
			goodsReceivingService.save(goodsReceivingList);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "{\"success\":true,\"data\":\"ok\"}";
	}
}
