package com.manage.service;

import java.util.List;
import java.util.Map;

import com.manage.model.ContractFile;
import com.manage.model.ContractInfo;
import com.manage.model.Payment;
import com.manage.model.WebPage;

public interface ContractService {

	Boolean saveOrUpdate(ContractInfo contractInfo,List<Payment> paymentList,List<ContractFile> contractFileList);

	WebPage<ContractInfo> getAll(Map<String, String> map,Boolean hasPage);

	ContractInfo getById(Integer id);

	ContractInfo getInfoById(Integer id);
	
	/**
	 * true：已存在；false：不存在
	 * @param map
	 * @return
	 */
	Boolean getExist(Map<String, String> map);

	ContractInfo getOnlyWithFilesById(Integer id);

	Boolean updateAuditing(ContractInfo contractInfo);

	/**
	 * 合同状态（0：待审核；1:最终审核通过；2：审核未通过；3：完成；4：合同取消；5:一级审核通过；6:可拆解状态；7:部门拆解进行中）
	 * @param contractInfoId
	 * @param status
	 * @return
	 */
	Boolean updateStatus(Integer contractInfoId,Integer status);

}
