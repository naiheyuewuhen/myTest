package com.manage.dao;

import java.util.Date;
import java.util.Map;

import com.manage.model.PurchaseOrder;
import com.manage.model.WebPage;

public interface PurchaseOrdersDao {

	PurchaseOrder getById(Integer id);

	WebPage<PurchaseOrder> getAll(Map<String, String> map, Boolean hasPage);

	Boolean saveBatch(Integer contractId, String orderNo, String resolveIds, String createUser, Date createTime);

	Integer updateUnuse(Integer contractId);

	Integer updateTempToUse(Integer contractId);

}
