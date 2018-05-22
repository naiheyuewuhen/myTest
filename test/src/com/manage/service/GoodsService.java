package com.manage.service;

import java.util.Map;

import com.manage.model.GoodsInfo;
import com.manage.model.WebPage;

public interface GoodsService {
	
	Boolean saveOrUpdate(GoodsInfo goodsInfo);

	WebPage<GoodsInfo> getAll(Map<String, String> map,Boolean hasPage);

	GoodsInfo getById(Integer id);
	
	/**
	 * true：已存在；false：不存在
	 * @param map
	 * @return
	 */
	Boolean getExist(Map<String, String> map);
}
