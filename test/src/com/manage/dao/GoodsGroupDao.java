package com.manage.dao;

import java.util.Map;

import com.manage.model.GoodsGroup;
import com.manage.model.WebPage;

public interface GoodsGroupDao {

	/**
	 * 返回自增主键id
	 * @param contractDetailInfo
	 * @return
	 */
	Integer save(GoodsGroup goodsGroup);

	WebPage<GoodsGroup> getAll(Map<String, String> map,Boolean hasPage);

	Integer update(GoodsGroup goodsGroup);

	GoodsGroup getById(Integer id);

	/**
	 * true：已存在；false：不存在
	 * @param map
	 * @return
	 */
	Boolean getExist(Map<String, String> map);

	Integer updateStatus(GoodsGroup goodsGroup);

}
