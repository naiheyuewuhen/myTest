package com.manage.dao;

import java.util.List;
import java.util.Map;

import com.manage.model.GoodsReceiving;
import com.manage.model.WebPage;

public interface GoodsReceivingDao {

	GoodsReceiving getById(Integer id);

	WebPage<GoodsReceiving> getAll(Map<String, Object> map, Boolean hasPage);

	List<GoodsReceiving> getAllForContract(Map<String, Object> map);

	Boolean saveBatch(List<GoodsReceiving> goodsReceivingList);

}
