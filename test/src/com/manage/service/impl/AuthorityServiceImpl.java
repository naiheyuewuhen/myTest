package com.manage.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.manage.dao.AuthorityDao;
import com.manage.model.Authority;
import com.manage.service.AuthorityService;

@Service("authorityService")
public class AuthorityServiceImpl implements AuthorityService{

	@Resource
	private AuthorityDao authorityDao;
	
	@Override
	public List<Integer> getModulesByRoleId(Integer roleId){
		return authorityDao.getModulesByRoleId(roleId);
	}
	@Override
	public Boolean save(Integer roleId,List<Authority> authorityList) {
		authorityDao.delete(roleId);
		authorityDao.saveBatch(authorityList);
		return true;
	}

	@Override
	public Integer delete(Integer roleId) {
		return authorityDao.delete(roleId);
	}

}
