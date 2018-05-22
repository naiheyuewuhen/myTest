package com.manage.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.manage.dao.GoodsGroupDao;
import com.manage.dao.GoodsGroupDetailDao;
import com.manage.model.GoodsGroup;
import com.manage.model.GoodsGroupDetail;
import com.manage.model.WebPage;
import com.manage.service.GoodsGroupService;

@Service("goodsGroupService")
public class GoodsGroupServiceImpl implements GoodsGroupService {

	@Resource
	private GoodsGroupDao goodsGroupDao;
	
	@Resource
	private GoodsGroupDetailDao goodsGroupDetailDao;
	
	@Override
	public Boolean saveOrUpdate(GoodsGroup goodsGroup,List<GoodsGroupDetail> goodsGroupDetailList) {
		//更新操作
		Integer groupId = null;
		List<GoodsGroupDetail> goodsGroupDetailListUpdate=new ArrayList<GoodsGroupDetail>();
		List<GoodsGroupDetail> goodsGroupDetailListInsert=new ArrayList<GoodsGroupDetail>();
		if(goodsGroup.getId()>0) {
			groupId=goodsGroup.getId();
			goodsGroupDao.update(goodsGroup);
		}else {
			groupId=goodsGroupDao.save(goodsGroup);
		}
		
		for(GoodsGroupDetail goodsGroupDetail : goodsGroupDetailList) {
			goodsGroupDetail.setStatus(1);
			goodsGroupDetail.setGroupId(groupId);
			if(goodsGroupDetail.getId()>0) {
				goodsGroupDetailListUpdate.add(goodsGroupDetail);
			}else {
				goodsGroupDetailListInsert.add(goodsGroupDetail);
			}
		}
		
		//先更新所关联的记录为无效，后续根据条件更新为有效或插入新的记录
		goodsGroupDetailDao.updateUnuse(groupId);
		goodsGroupDetailDao.updateBatch(goodsGroupDetailListUpdate);
		goodsGroupDetailDao.saveBatch(goodsGroupDetailListInsert);
		return true;
	}

	@Override
	public WebPage<GoodsGroup> getAll(Map<String, String> map, Boolean hasPage) {
		return goodsGroupDao.getAll(map,hasPage);
	}

	@Override
	public GoodsGroup getById(Integer id) {
		Map<String, String> map=new HashMap<String,String>();
		map.put("groupId", id.toString());
		GoodsGroup goodsGroup = goodsGroupDao.getById(id);
		List<GoodsGroupDetail> list = goodsGroupDetailDao.getAll(map,false).getRows();
		goodsGroup.setGoodsGroupDetailList(list);
		return goodsGroup;
	}
	@Override
	public Integer updateStatus(GoodsGroup goodsGroup) {
		return goodsGroupDao.updateStatus(goodsGroup);
	}

	@Override
	public Boolean getExist(Map<String, String> map) {
		return goodsGroupDao.getExist(map);
	}

}
