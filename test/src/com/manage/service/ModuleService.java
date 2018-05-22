package com.manage.service;

import java.util.List;

import com.manage.model.ModuleInfo;

public interface ModuleService {

	List<ModuleInfo> getByFatherId(Integer fatherId, Integer roleId);

	List<ModuleInfo> getTreeAll();

	List<ModuleInfo> getButtonByRoleId(Integer roleId);

}
