package com.manage.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.manage.model.ContactResolve;
import com.manage.model.ContractGoods;
import com.manage.model.WebPage;

public interface ContactResolveService {

	/**
	 * 返回自增主键id
	 * @param contractDetailInfo
	 * @return
	 */
	Boolean saveOrUpdate(ContactResolve contactResolve,List<ContractGoods> contractGoodsList);

	WebPage<ContactResolve> getAll(Map<String, String> map,Boolean hasPage);

	ContactResolve getById(Integer id);

	/**
	 * true：已存在；false：不存在
	 * @param map
	 * @return
	 */
	Boolean getExist(Map<String, String> map);

	Integer updateStatus(ContactResolve contactResolve);

	boolean updateForReality(ContactResolve contactResolve, List<ContractGoods> contractGoodsList);

	Boolean saveOrUpdateBatch(Integer contractId, List<ContactResolve> contactResolveList);

	Boolean saveToPurchaseOrders(Integer contractId, String orderNo, String resolveIds, String createUser,
			Date createTime);

}
