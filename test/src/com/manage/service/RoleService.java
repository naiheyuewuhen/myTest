package com.manage.service;

import java.util.Map;

import com.manage.model.Role;
import com.manage.model.WebPage;

public interface RoleService {
	
	Role getById(Integer id);

	WebPage<Role> getAll(Map<String, String> map, Boolean hasPage);

	Boolean getExist(Map<String, String> map);

	Boolean saveOrUpdate(Role role);
}
