package com.manage.service;

import java.util.Map;

import com.manage.model.User;
import com.manage.model.WebPage;

public interface UserService {
	
	User getById(Integer id);

	WebPage<User> getAll(Map<String, String> map, Boolean hasPage);

	Boolean getExist(Map<String, String> map);

	Boolean saveOrUpdate(User user);
}
