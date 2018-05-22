package com.manage.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.manage.model.ModuleInfo;
import com.manage.model.User;
import com.manage.service.ModuleService;

@RequestMapping("/module")
@Controller
public class ModuleController {
	
	@Resource
	private ModuleService moduleService;

	@ResponseBody
	@RequestMapping("/getAllByFatherId")
	public List<ModuleInfo> getAllByFatherId(@RequestParam("fatherId") Integer fatherId,HttpSession session){
		Integer systemUser=(Integer) session.getAttribute("systemUser");
		if(systemUser==1) {
			return moduleService.getByFatherId(fatherId,null);
		}else {
			User user=(User) session.getAttribute("user");
			return moduleService.getByFatherId(fatherId,user.getRoleId());
		}
	}
	@ResponseBody
	@RequestMapping("/getTreeAll")
	public List<ModuleInfo> getTreeAll(){
		return moduleService.getTreeAll();
	}
}
