package com.manage.dao;

import java.util.List;
import java.util.Map;

import com.manage.model.GoodsGroupDetail;
import com.manage.model.WebPage;

public interface GoodsGroupDetailDao {

	Boolean saveBatch(List<GoodsGroupDetail> goodsGroupDetailList);

	/**
	 * 更新状态为失效
	 * @param groupId
	 * @return
	 */
	Integer updateUnuse(Integer groupId);

	Boolean updateBatch(List<GoodsGroupDetail> goodsGroupDetailList);

	WebPage<GoodsGroupDetail> getAll(Map<String,String> map,Boolean hasPage);

}
