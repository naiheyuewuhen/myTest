package com.manage.service;

import java.util.Map;

import com.manage.model.ContractPayStandard;
import com.manage.model.WebPage;

public interface ContractPayStandardService {

	ContractPayStandard getById(Integer id);

	WebPage<ContractPayStandard> getAll(Map<String, String> map, Boolean hasPage);

	Boolean saveOrUpdate(ContractPayStandard model);

	Boolean getExist(Map<String, String> map);

}
