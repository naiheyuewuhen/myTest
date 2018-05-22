package com.manage.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.manage.model.GoodsGroup;
import com.manage.model.GoodsGroupDetail;
import com.manage.model.WebPage;
import com.manage.service.GoodsGroupService;

@RequestMapping("/goodsgroup")
@Controller
public class GoodsGroupController {
	
	@Resource
	private GoodsGroupService goodsGroupService;

	@ResponseBody
	@RequestMapping(value="/getById")
	public GoodsGroup getById(@RequestParam("id") Integer id){
		return goodsGroupService.getById(id);
	}
	@ResponseBody
	@RequestMapping(value="/getAll")
	public List<GoodsGroup> getAll(@RequestParam Map<String, String> map){
		return goodsGroupService.getAll(map,false).getRows();
	}
	@ResponseBody
	@RequestMapping(value="/getAllpage")
	public WebPage<GoodsGroup> getAllWithPage(@RequestParam Map<String, String> map){
		return goodsGroupService.getAll(map,true);
	}
	@ResponseBody
	@RequestMapping("/autocomplete")
	public List<GoodsGroup> getAll(@RequestParam(defaultValue="") String q){
		if(q.equals("")) {
			return new ArrayList<>();
		}else {
			Map<String,String> map=new HashMap<>();
			map.put("code", q);
			map.put("status", "1");
			return goodsGroupService.getAll(map,false).getRows();
		}
	}
	@ResponseBody
	@RequestMapping(value="/delete", produces = "application/json; charset=utf-8")
	public String delete(@RequestParam("id") Integer id){
		GoodsGroup goodsGroup = new GoodsGroup();
		goodsGroup.setId(id);
		goodsGroup.setStatus(0);
		goodsGroupService.updateStatus(goodsGroup);
		return "{\"success\":true,\"data\":\"ok\"}";
	}
	@ResponseBody
	@RequestMapping(value="/save", produces = "application/json; charset=utf-8")
	public String save(GoodsGroup goodsGroup,@RequestParam("goodsList") String goodsList) {
		Map<String,String> map=new HashMap<>();
		map.put("name", goodsGroup.getCode());
		if(goodsGroup.getId()>0) {
			map.put("id", goodsGroup.getId().toString());
		}
		if(goodsGroupService.getExist(map)) {
			return "{\"success\":false,\"data\":\""+goodsGroup.getCode()+"已存在\"}";//返回是乱码，需要处理
		}
		ObjectMapper mapper = new ObjectMapper();  
		List<GoodsGroupDetail> goodsGroupDetailList;
			try {
				goodsGroupDetailList = mapper.readValue(goodsList, new TypeReference<List<GoodsGroupDetail>>(){});
				goodsGroupService.saveOrUpdate(goodsGroup,goodsGroupDetailList);
			
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
