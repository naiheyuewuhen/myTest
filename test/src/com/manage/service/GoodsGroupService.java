package com.manage.service;

import java.util.List;
import java.util.Map;

import com.manage.model.GoodsGroup;
import com.manage.model.GoodsGroupDetail;
import com.manage.model.WebPage;

public interface GoodsGroupService {

	/**
	 * 返回自增主键id
	 * @param contractDetailInfo
	 * @return
	 */
	Boolean saveOrUpdate(GoodsGroup goodsGroup,List<GoodsGroupDetail> goodsGroupDetailList);

	WebPage<GoodsGroup> getAll(Map<String, String> map,Boolean hasPage);

	GoodsGroup getById(Integer id);

	/**
	 * true：已存在；false：不存在
	 * @param map
	 * @return
	 */
	Boolean getExist(Map<String, String> map);

	Integer updateStatus(GoodsGroup goodsGroup);

}
