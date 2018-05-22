package com.manage.dao;

import java.util.Map;

import com.manage.model.ContractInfo;
import com.manage.model.WebPage;

public interface ContractDao {

	/**
	 * 返回自增主键id
	 * @param contractInfo
	 * @return
	 */
	Integer save(ContractInfo contractInfo);

	WebPage<ContractInfo> getAll(Map<String, String> map,Boolean hasPage);

	Integer update(ContractInfo contractInfo);

	ContractInfo getById(Integer id);

	/**
	 * true：已存在；false：不存在
	 * @param map
	 * @return
	 */
	Boolean getExist(Map<String, String> map);

	Integer updateAuditing(ContractInfo contractInfo);

	/**
	 * 合同状态（0：待审核；1:最终审核通过；2：审核未通过；3：完成；4：合同取消；5:一级审核通过；6:可拆解状态；7:部门拆解进行中）
	 * @param id
	 * @param status
	 * @return
	 */
	Integer updateStatus(Integer contractInfoId,Integer status);

}
