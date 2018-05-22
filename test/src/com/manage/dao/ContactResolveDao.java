package com.manage.dao;

import java.util.List;
import java.util.Map;

import com.manage.model.ContactResolve;
import com.manage.model.WebPage;

public interface ContactResolveDao {

	/**
	 * 返回自增主键id
	 * @param contractDetailInfo
	 * @return
	 */
	Integer save(ContactResolve contactResolve);

	WebPage<ContactResolve> getAll(Map<String, String> map,Boolean hasPage);

	Integer update(ContactResolve contactResolve);

	ContactResolve getById(Integer id);

	/**
	 * true：已存在；false：不存在
	 * @param map
	 * @return
	 */
	Boolean getExist(Map<String, String> map);

	Integer updateStatus(ContactResolve contactResolve);

	Boolean saveBatch(List<ContactResolve> contactResolveList);

	Integer updateUnuse(Integer contractId);

	Boolean updateBatch(List<ContactResolve> contactResolveList);

	Integer updateStatusByIds(String ids,Integer status);

}
