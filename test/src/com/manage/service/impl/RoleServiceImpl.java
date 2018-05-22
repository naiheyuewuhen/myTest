package com.manage.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.manage.dao.RoleDao;
import com.manage.model.Role;
import com.manage.model.WebPage;
import com.manage.service.RoleService;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

	@Resource
	private RoleDao roleDao;
	
	@Override
	public Role getById(Integer id) {
		return roleDao.getById(id);
	}

	@Override
	public WebPage<Role> getAll(Map<String, String> map, Boolean hasPage) {
		return roleDao.getAll(map, hasPage);
	}

	@Override
	public Boolean saveOrUpdate(Role role) {
		if(role.getId()!=null&&role.getId()>0) {
			roleDao.update(role);
		}else {
			roleDao.save(role);
		}
		return true;
	}
	
	@Override
	public Boolean getExist(Map<String, String> map) {
		return roleDao.getExist(map);
	}

}
