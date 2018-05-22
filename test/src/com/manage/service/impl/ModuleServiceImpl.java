package com.manage.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.manage.dao.ModuleDao;
import com.manage.model.ModuleInfo;
import com.manage.service.ModuleService;

@Service("moduleService")
public class ModuleServiceImpl implements ModuleService {

	@Resource
	private ModuleDao moduleDao; 
	@Override
	public List<ModuleInfo> getByFatherId(Integer fatherId, Integer roleId) {
		return moduleDao.getByFatherId(fatherId, roleId);
	}
	@Override
	public List<ModuleInfo> getButtonByRoleId(Integer roleId) {
		return moduleDao.getButtonByRoleId(roleId);
	}
	@Override
	public List<ModuleInfo> getTreeAll() {
		return moduleDao.getTreeAll();
	}
}
