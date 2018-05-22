package com.manage.dao;

import java.util.List;

import com.manage.model.ModuleInfo;

public interface ModuleDao {

	List<ModuleInfo> getByFatherId(Integer fatherId,Integer roleId);

	List<ModuleInfo> getTreeAll();

	List<ModuleInfo> getButtonByRoleId(Integer roleId);

}
