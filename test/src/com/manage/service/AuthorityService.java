package com.manage.service;

import java.util.List;

import com.manage.model.Authority;

public interface AuthorityService {

	public Boolean save(Integer roleId,List<Authority> authorityList);

	Integer delete(Integer roleId);

	List<Integer> getModulesByRoleId(Integer roleId);

}
