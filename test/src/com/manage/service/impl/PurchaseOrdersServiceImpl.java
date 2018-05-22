package com.manage.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.manage.dao.PurchaseOrderDetailDao;
import com.manage.dao.PurchaseOrdersDao;
import com.manage.model.PurchaseOrder;
import com.manage.model.PurchaseOrderDetail;
import com.manage.model.WebPage;
import com.manage.service.PurchaseOrdersService;

@Service("purchaseOrdersService")
public class PurchaseOrdersServiceImpl implements PurchaseOrdersService {

	@Resource
	PurchaseOrdersDao purchaseOrdersDao;
	@Resource
	private PurchaseOrderDetailDao purchaseOrderDetailDao;
	@Override
	public PurchaseOrder getById(Integer id) {
		PurchaseOrder purchaseOrder=purchaseOrdersDao.getById(id);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("purchaseOrderId", id);
		List<PurchaseOrderDetail> purchaseOrderDetailList = purchaseOrderDetailDao.getAll(map);
		purchaseOrder.setPurchaseOrderDetailList(purchaseOrderDetailList);
		return purchaseOrder;
	}

	@Override
	public WebPage<PurchaseOrder> getAll(Map<String, String> map, Boolean hasPage) {
		return purchaseOrdersDao.getAll(map, hasPage);
	}

}
