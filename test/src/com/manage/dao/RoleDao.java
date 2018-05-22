package com.manage.dao;

import java.util.Map;

import com.manage.model.Role;
import com.manage.model.WebPage;

public interface RoleDao {

	Role getById(Integer id);

	WebPage<Role> getAll(Map<String, String> map, Boolean hasPage);

	Integer save(Role role);

	Integer update(Role role);

	Boolean getExist(Map<String, String> map);

}
