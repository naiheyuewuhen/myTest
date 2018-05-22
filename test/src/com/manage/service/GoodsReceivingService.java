package com.manage.service;

import java.util.List;
import java.util.Map;

import com.manage.model.GoodsReceiving;
import com.manage.model.WebPage;

public interface GoodsReceivingService {

	Boolean save(List<GoodsReceiving> goodsReceivingList);

	WebPage<GoodsReceiving> getAll(Map<String, Object> map, Boolean hasPage);

	GoodsReceiving getById(Integer id);

	List<GoodsReceiving> getAllForContract(Map<String, Object> map);

}
