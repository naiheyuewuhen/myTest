package com.manage.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.manage.dao.GoodsReceivingDao;
import com.manage.model.GoodsReceiving;
import com.manage.model.WebPage;
import com.manage.service.GoodsReceivingService;

@Service("goodsReceivingService")
public class GoodsReceivingServiceImpl implements GoodsReceivingService {

	@Resource
	private GoodsReceivingDao goodsReceivingDao;
	
	@Override
	public Boolean save(List<GoodsReceiving> goodsReceivingList) {
		goodsReceivingDao.saveBatch(goodsReceivingList);
		return true;
	}
	@Override
	public WebPage<GoodsReceiving> getAll(Map<String, Object> map, Boolean hasPage) {
		return goodsReceivingDao.getAll(map,hasPage);
	}

	@Override
	public GoodsReceiving getById(Integer id) {
		GoodsReceiving goodsReceiving = goodsReceivingDao.getById(id);
		return goodsReceiving;
	}
	@Override
	public List<GoodsReceiving> getAllForContract(Map<String,Object> map){
		return goodsReceivingDao.getAllForContract(map);
	}
}
