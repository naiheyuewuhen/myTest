package com.manage.service;

import java.util.Map;

import com.manage.model.PurchaseOrder;
import com.manage.model.WebPage;

public interface PurchaseOrdersService {

	PurchaseOrder getById(Integer id);

	WebPage<PurchaseOrder> getAll(Map<String, String> map, Boolean hasPage);


}
