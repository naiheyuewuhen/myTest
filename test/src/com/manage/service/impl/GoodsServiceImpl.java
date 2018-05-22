package com.manage.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.manage.dao.GoodsDao;
import com.manage.dao.GoodsSupplierDao;
import com.manage.model.GoodsInfo;
import com.manage.model.WebPage;
import com.manage.service.GoodsService;

@Service("goodsService")
public class GoodsServiceImpl implements GoodsService {

	@Resource
	private GoodsDao goodsDao;
	@Resource
	private GoodsSupplierDao goodsSupplierDao;
	@Override
	public Boolean saveOrUpdate(GoodsInfo goodsInfo) {
		if(goodsInfo.getId()>0) {
			goodsDao.update(goodsInfo);
		}else {
			goodsDao.save(goodsInfo);
		}
		return true;
	}

	@Override
	public WebPage<GoodsInfo> getAll(Map<String, String> map,Boolean hasPage) {
		return goodsDao.getAll(map,hasPage);
	}
	@Override
	public GoodsInfo getById(Integer id) {
		GoodsInfo goodsInfo=goodsDao.getById(id);
		if(goodsInfo!=null) {
			Map<String,String> map=new HashMap<>();
			map.put("goodsId", id.toString());
			goodsInfo.setGoodsSupplierInfoList(goodsSupplierDao.getAllByGoodsId(map, false).getRows());
		}
		return goodsInfo;
	}

	@Override
	public Boolean getExist(Map<String, String> map) {
		return goodsDao.getExist(map);
	}

}
