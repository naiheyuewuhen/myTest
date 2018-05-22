package com.manage.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
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
import com.manage.model.ContactResolve;
import com.manage.model.ContractGoods;
import com.manage.model.WebPage;
import com.manage.service.ContactResolveService;

@RequestMapping("/contractresolve")
@Controller
public class ContactResolveController {
	
	@Resource
	private ContactResolveService contactResolveService;

	@ResponseBody
	@RequestMapping(value="/getById")
	public ContactResolve getById(@RequestParam("id") Integer id){
		return contactResolveService.getById(id);
	}
	@ResponseBody
	@RequestMapping(value="/getAll")
	public List<ContactResolve> getAll(@RequestParam Map<String, String> map){
		return contactResolveService.getAll(map,false).getRows();
	}
	@ResponseBody
	@RequestMapping(value="/getAllpage")
	public WebPage<ContactResolve> getAllWithPage(@RequestParam Map<String, String> map){
		return contactResolveService.getAll(map,true);
	}
	@ResponseBody
	@RequestMapping(value="/delete", produces = "application/json; charset=utf-8")
	public String delete(@RequestParam("id") Integer id,HttpSession session){
		ContactResolve contactResolve = new ContactResolve();
		contactResolve.setId(id);
		contactResolve.setStatus(0);
//		contactResolve.setUpdateUser("admin");
		contactResolve.setUpdateUser((String)session.getAttribute("userName"));
		contactResolve.setUpdateTime(new Date());
		contactResolveService.updateStatus(contactResolve);
		return "{\"success\":true,\"data\":\"ok\"}";
	}
	@ResponseBody
	@RequestMapping(value="/buildorder", produces = "application/json; charset=utf-8")
	public String buildorder(@RequestParam("resolveIds") String resolveIds,@RequestParam("contractId") Integer contractId,HttpSession session){
		String createUser=(String)session.getAttribute("userName");
		Date createTime=new Date();
		String orderNo="GD"+System.currentTimeMillis();
		contactResolveService.saveToPurchaseOrders(contractId, orderNo, resolveIds, createUser, createTime);
		return "{\"success\":true,\"data\":\"ok\"}";
	}
	@ResponseBody
	@RequestMapping(value="/save", produces = "application/json; charset=utf-8")
	public String save(ContactResolve contactResolve,@RequestParam("goodsList") String goodsList,HttpSession session) {
		Map<String,String> map=new HashMap<>();
		map.put("resolveName", contactResolve.getResolveName());
		map.put("contractId", contactResolve.getContractId().toString());
		if(contactResolve.getId()>0) {
			map.put("id", contactResolve.getId().toString());
		}
		if(contactResolveService.getExist(map)) {
			return "{\"success\":false,\"data\":\""+contactResolve.getResolveName()+"已存在\"}";//返回是乱码，需要处理
		}
		Date date=new Date();
//		Gson gson = new Gson();
//		List<ContractGoods> contractGoodsList = gson.fromJson(goodsList, new TypeToken<List<ContractGoods>>(){}.getType());  
		
		ObjectMapper mapper = new ObjectMapper();  
		List<ContractGoods> contractGoodsList;
			try {
				contractGoodsList = mapper.readValue(goodsList, new TypeReference<List<ContractGoods>>(){});
				contactResolve.setCreateUser((String)session.getAttribute("userName"));
				contactResolve.setCreateTime(date);
				contactResolve.setUpdateUser((String)session.getAttribute("userName"));
				contactResolve.setUpdateTime(date);
				for(ContractGoods contractGoods : contractGoodsList) {
					contractGoods.setStatus(1);
					contractGoods.setCreateUser((String)session.getAttribute("userName"));
					contractGoods.setCreateTime(date);
					contractGoods.setUpdateUser((String)session.getAttribute("userName"));
					contractGoods.setUpdateTime(date);
				}
				contactResolveService.saveOrUpdate(contactResolve,contractGoodsList);
			
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
	@ResponseBody
	@RequestMapping(value="/savebatch", produces = "application/json; charset=utf-8")
	public String saveBatch(@RequestParam("contractId") Integer contractId,@RequestParam("resolveList") String resolveList,HttpSession session) {
		Date date=new Date();
		ObjectMapper mapper = new ObjectMapper();  
		List<ContactResolve> list;
		try {
			list = mapper.readValue(resolveList, new TypeReference<List<ContactResolve>>(){});
			for(ContactResolve contactResolve : list) {
				contactResolve.setContractId(contractId);
				contactResolve.setStatus(2);//默认为未拆解完成的状态
				contactResolve.setCreateUser((String)session.getAttribute("userName"));
				contactResolve.setCreateTime(date);
				contactResolve.setUpdateUser((String)session.getAttribute("userName"));
				contactResolve.setUpdateTime(date);
			}
			contactResolveService.saveOrUpdateBatch(contractId,list);
			
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
	@ResponseBody
	@RequestMapping(value="updateForReality", produces = "application/json; charset=utf-8")
	public String updateForReality(ContactResolve contactResolve,@RequestParam("goodsList") String goodsList,HttpSession session) {
		Date date=new Date();
		ObjectMapper mapper = new ObjectMapper();  
		List<ContractGoods> contractGoodsList;
		try {
			contractGoodsList = mapper.readValue(goodsList, new TypeReference<List<ContractGoods>>(){});
			contactResolve.setUpdateUser((String)session.getAttribute("userName"));
			contactResolve.setUpdateTime(date);
			for(ContractGoods contractGoods : contractGoodsList) {
				contractGoods.setRealityUser((String)session.getAttribute("userName"));
				contractGoods.setRealityTime(date);
			}
			contactResolveService.updateForReality(contactResolve, contractGoodsList);
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
