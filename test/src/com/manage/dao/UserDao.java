package com.manage.dao;

import java.util.Map;

import com.manage.model.User;
import com.manage.model.WebPage;

public interface UserDao {

	User getById(Integer id);

	WebPage<User> getAll(Map<String, String> map, Boolean hasPage);

	Integer save(User user);

	Integer update(User user);

	Boolean getExist(Map<String, String> map);

}
