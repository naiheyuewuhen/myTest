package com.manage.dao;

import java.util.List;

import com.manage.model.Authority;

public interface AuthorityDao {

	List<Integer> getModulesByRoleId(Integer roleId);
	
	Integer delete(Integer roleId);

	Boolean saveBatch(List<Authority> authorityList);

}
