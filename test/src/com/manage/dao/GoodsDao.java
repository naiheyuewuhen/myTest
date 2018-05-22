package com.manage.dao;

import java.util.Map;

import com.manage.model.GoodsInfo;
import com.manage.model.WebPage;

public interface GoodsDao {

	GoodsInfo getById(Integer id);

	WebPage<GoodsInfo> getAll(Map<String, String> map, Boolean hasPage);

	Integer save(GoodsInfo goodsInfo);

	Integer update(GoodsInfo goodsInfo);

	/**
	 * true：已存在；false：不存在
	 * @param map
	 * @return
	 */
	Boolean getExist(Map<String, String> map);

}
