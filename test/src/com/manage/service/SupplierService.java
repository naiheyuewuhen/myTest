package com.manage.service;

import java.util.List;
import java.util.Map;

import com.manage.model.SupplierInfo;
import com.manage.model.WebPage;

public interface SupplierService {
	
	Boolean saveOrUpdate(SupplierInfo supplierInfo);

	WebPage<SupplierInfo> getAll(Map<String, String> map,Boolean hasPage);

	SupplierInfo getById(Integer id,Boolean onlyValidGoodsSupplier);
	
	/**
	 * true：已存在；false：不存在
	 * @param map
	 * @return
	 */
	Boolean getExist(Map<String, String> map);
	
	List<SupplierInfo> getAllInIds(String ids);

	List<SupplierInfo> getAllInContractByContractId(Integer contractId,Map<String,String> map);
}
