package com.manage.dao;

import java.util.Date;
import java.util.Map;

import com.manage.model.ContractPayStandard;
import com.manage.model.WebPage;

public interface ContractPayStandardDao {

	ContractPayStandard getById(Integer id);

	WebPage<ContractPayStandard> getAll(Map<String, String> map, Boolean hasPage);

	Integer save(ContractPayStandard model);

	Integer update(ContractPayStandard model);

	Boolean getExist(Map<String, String> map);

	ContractPayStandard getByNow(Date now);

}
