package com.manage.dao;

import java.util.List;
import java.util.Map;

import com.manage.model.PurchaseOrderDetail;

public interface PurchaseOrderDetailDao {

	List<PurchaseOrderDetail> getAll(Map<String, Object> map);

	Boolean saveBatch(Integer contractId,String resolveIds);

}
