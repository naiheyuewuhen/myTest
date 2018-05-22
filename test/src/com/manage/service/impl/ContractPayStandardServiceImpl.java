package com.manage.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.manage.dao.ContractPayStandardDao;
import com.manage.model.ContractPayStandard;
import com.manage.model.WebPage;
import com.manage.service.ContractPayStandardService;

@Service("contractPayStandardService")
public class ContractPayStandardServiceImpl implements ContractPayStandardService {

	@Resource
	private ContractPayStandardDao contractPayStandardDao;
	@Override
	public ContractPayStandard getById(Integer id) {
		return contractPayStandardDao.getById(id);
	}

	@Override
	public WebPage<ContractPayStandard> getAll(Map<String, String> map, Boolean hasPage) {
		return contractPayStandardDao.getAll(map, hasPage);
	}

	@Override
	public Boolean saveOrUpdate(ContractPayStandard model) {
		if(model.getId()>0) {
			contractPayStandardDao.update(model);
		}else {
			contractPayStandardDao.save(model);
		}
		return true;
	}

	@Override
	public Boolean getExist(Map<String, String> map) {
		return contractPayStandardDao.getExist(map);
	}

}
