package com.manage.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.manage.model.Authority;
import com.manage.service.AuthorityService;

@RequestMapping("/authority")
@Controller
public class AuthorityController {
	
	@Resource
	private AuthorityService authorityService;

	@ResponseBody
	@RequestMapping("/getModulesByRoleId")
	public List<Integer> getModulesByRoleId(@RequestParam Integer roleId){
		return authorityService.getModulesByRoleId(roleId);
	}
	@ResponseBody
	@RequestMapping(value="/save", produces = "application/json; charset=utf-8")
	public String save(@RequestParam(name="roleId") Integer roleId,@RequestParam(name = "moduleIds[]") Integer[] moduleIds) {
		List<Authority> authorityList=new ArrayList<Authority>();
		for(Integer moduleId : moduleIds) {
			Authority authority=new Authority();
			authority.setRoleId(roleId);
			authority.setModuleId(moduleId);
			authorityList.add(authority);
		}
		authorityService.save(roleId,authorityList);
		return "{\"success\":true,\"data\":\"ok\"}";
	}
}
