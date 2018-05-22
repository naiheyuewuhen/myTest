package com.manage.service;

import java.util.List;
import java.util.Map;

import com.manage.model.PurchaseOrderDetail;

public interface PurchaseOrderDetailService {

	List<PurchaseOrderDetail> getAll(Map<String, Object> map);
}
