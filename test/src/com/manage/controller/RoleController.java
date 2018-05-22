package com.manage.controller;

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

import com.manage.model.Role;
import com.manage.model.WebPage;
import com.manage.service.RoleService;

@RequestMapping("/role")
@Controller
public class RoleController {
	
	@Resource
	private RoleService roleService;

	@ResponseBody
	@RequestMapping("/getById")
	public Role getById(@RequestParam Integer id){
		return roleService.getById(id);
	}
	@ResponseBody
	@RequestMapping("/getAll")
	public List<Role> getAll(@RequestParam Map<String, String> map){
		return roleService.getAll(map,false).getRows();
	}
	@ResponseBody
	@RequestMapping("/getAllpage")
	public WebPage<Role> getAllWithPage(@RequestParam Map<String, String> map){
		return roleService.getAll(map,true);
	}
	@ResponseBody
	@RequestMapping(value="/save", produces = "application/json; charset=utf-8")
	public String save(Role role,HttpSession session) {
		Map<String,String> map=new HashMap<>();
		map.put("name", role.getName());
		if(role.getId()!=null&&role.getId()>0) {
			map.put("id", role.getId().toString());
		}
		if(roleService.getExist(map)) {
			return "{\"success\":false,\"data\":\"编号已存在\"}";
		}
		Date date=new Date();
//		goodsInfo.setStatus(1);
		role.setCreateTime(date);
		role.setCreateUser((String)session.getAttribute("userName"));
		role.setUpdateTime(date);
		role.setUpdateUser((String)session.getAttribute("userName"));
		roleService.saveOrUpdate(role);
		return "{\"success\":true,\"data\":\"ok\"}";
	}
}
