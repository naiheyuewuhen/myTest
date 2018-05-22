package com.manage.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.manage.model.ModuleInfo;
import com.manage.model.User;
import com.manage.service.ModuleService;
import com.manage.service.UserService;

@RequestMapping("/login")
@Controller
public class LoginController {
	
	@Resource
	private ModuleService moduleService;
	
	@Resource
	private UserService userService;
	
	@ResponseBody
	@RequestMapping(value="/userLogin", produces = "application/json; charset=utf-8")
	public String getModulesByRoleId(@RequestParam String loginUserName,@RequestParam String password,HttpSession session){
		session.setMaxInactiveInterval(10*24*60 * 60);
		JsonObject result= new JsonObject();
		Gson gson=new Gson();
		result.addProperty("success", false);
		if(loginUserName.equals("system")&&password.equals("110120")) {//管理员
			session.setAttribute("systemUser", 1);
			session.setAttribute("userName", "system");
			List<ModuleInfo> moduleList=moduleService.getButtonByRoleId(null);
			Map<String,Integer> authMap=new HashMap<String,Integer>();//用String类型存储key，是为了解决EL表达式不支持Integer类型的key
			for(ModuleInfo moduleInfo:moduleList) {
				authMap.put(""+moduleInfo.getId(), 1);
			}
			session.setAttribute("authMap", authMap);//按钮级别的权限 
			result.addProperty("success", true);
			result.addProperty("url", "/views/main.jsp");
		}else {//普通角色用户
			Map<String,String> map=new HashMap<String,String>();
			map.put("username", loginUserName);
			map.put("password", password);
			List<User> userList= userService.getAll(map, false).getRows();
			if(userList.isEmpty()) {
				result.addProperty("msg", "登录信息错误");
				result.addProperty("code", "passwordError");
			}else {
				session.setAttribute("systemUser", 0);
				session.setAttribute("userName", userList.get(0).getUsername());
				session.setAttribute("user", userList.get(0));
				List<ModuleInfo> moduleList=moduleService.getButtonByRoleId(userList.get(0).getRoleId());
				Map<String,Integer> authMap=new HashMap<String,Integer>();//用String类型存储key，是为了解决EL表达式不支持Integer类型的key
				for(ModuleInfo moduleInfo:moduleList) {
					authMap.put(""+moduleInfo.getId(), 1);
				}
				session.setAttribute("authMap", authMap);//按钮级别的权限 
				result.addProperty("success", true);
				result.addProperty("url", "/views/main.jsp");
			}
		}
		return gson.toJson(result);
//		return "{\"success\":true,\"data\":\"ok\"}";
	}
}
