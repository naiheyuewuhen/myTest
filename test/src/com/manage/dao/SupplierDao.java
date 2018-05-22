package com.manage.dao;

import java.util.List;
import java.util.Map;

import com.manage.model.SupplierInfo;
import com.manage.model.WebPage;

public interface SupplierDao {

	SupplierInfo getById(Integer id);

	WebPage<SupplierInfo> getAll(Map<String, String> map, Boolean hasPage);

	Integer save(SupplierInfo supplierInfo);

	Integer update(SupplierInfo supplierInfo);

	/**
	 * true：已存在；false：不存在
	 * @param map
	 * @return
	 */
	Boolean getExist(Map<String, String> map);

	List<SupplierInfo> getAllInIds(String ids);

	List<SupplierInfo> getAllInContractByContractId(Integer contractId,Map<String,String> map);

}
