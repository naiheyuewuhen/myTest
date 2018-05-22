package com.manage.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.manage.dao.UserDao;
import com.manage.model.User;
import com.manage.model.WebPage;
import com.manage.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService{
	
	@Resource
	private UserDao userDao;

	@Override
	public User getById(Integer id) {
		return userDao.getById(id);
	}

	@Override
	public WebPage<User> getAll(Map<String, String> map, Boolean hasPage) {
		return userDao.getAll(map, hasPage);
	}

	@Override
	public Boolean saveOrUpdate(User user) {
		if(user.getId()!=null&&user.getId()>0) {
			userDao.update(user);
		}else {
			userDao.save(user);
		}
		return true;
	}

	@Override
	public Boolean getExist(Map<String, String> map) {
		return userDao.getExist(map);
	} 
}
