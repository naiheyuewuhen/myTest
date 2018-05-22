package com.manage.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.manage.dao.PurchaseOrderDetailDao;
import com.manage.model.PurchaseOrderDetail;
import com.manage.service.PurchaseOrderDetailService;

@Service("purchaseOrderDetailService")
public class PurchaseOrderDetailServiceImpl implements PurchaseOrderDetailService {

	@Resource
	private PurchaseOrderDetailDao purchaseOrderDetailDao;
	@Override
	public List<PurchaseOrderDetail> getAll(Map<String, Object> map) {
		return purchaseOrderDetailDao.getAll(map);
	}

}
