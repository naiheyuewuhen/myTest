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

import com.manage.model.User;
import com.manage.model.WebPage;
import com.manage.service.UserService;

@RequestMapping("/user")
@Controller
public class UserController {
	
	@Resource
	private UserService userService;

	@ResponseBody
	@RequestMapping("/getById")
	public User getById(@RequestParam Integer id){
		return userService.getById(id);
	}
	@ResponseBody
	@RequestMapping("/getAll")
	public List<User> getAll(@RequestParam Map<String, String> map){
		return userService.getAll(map,false).getRows();
	}
	@ResponseBody
	@RequestMapping("/getAllpage")
	public WebPage<User> getAllWithPage(@RequestParam Map<String, String> map){
		return userService.getAll(map,true);
	}
	@ResponseBody
	@RequestMapping(value="/save", produces = "application/json; charset=utf-8")
	public String save(User user,HttpSession session) {
		Map<String,String> map=new HashMap<>();
		map.put("name", user.getName());
		if(user.getId()!=null&&user.getId()>0) {
			map.put("id", user.getId().toString());
		}
		if(userService.getExist(map)) {
			return "{\"success\":false,\"data\":\"编号已存在\"}";
		}
		Date date=new Date();
//		goodsInfo.setStatus(1);
		user.setCreateTime(date);
		user.setCreateUser((String)session.getAttribute("userName"));
		user.setUpdateTime(date);
		user.setUpdateUser((String)session.getAttribute("userName"));
		userService.saveOrUpdate(user);
		return "{\"success\":true,\"data\":\"ok\"}";
	}
}
