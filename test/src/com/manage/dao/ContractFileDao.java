package com.manage.dao;

import java.util.List;
import java.util.Map;

import com.manage.model.ContractFile;
import com.manage.model.WebPage;

public interface ContractFileDao {

	Boolean saveBatch(List<ContractFile> contractFileList);

	/**
	 * 更新状态为失效
	 * @param contractId
	 * @return
	 */
	Integer updateUnuse(Integer contractId);

	Boolean updateBatch(List<ContractFile> contractFileList);

	WebPage<ContractFile> getAll(Map<String,String> map,Boolean hasPage);

}
