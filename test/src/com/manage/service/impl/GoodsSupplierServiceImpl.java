package com.manage.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.manage.dao.GoodsSupplierDao;
import com.manage.model.GoodsSupplierInfo;
import com.manage.service.GoodsSupplierService;

@Service("goodsSupplierService")
public class GoodsSupplierServiceImpl implements GoodsSupplierService {
	@Resource
	private GoodsSupplierDao goodsSupplierDao;
	@Override
	public List<GoodsSupplierInfo> getAllByGoodsId(Map<String, String> map){
//		Map<String,String> map=new HashMap<>();
//		map.put("goodsId", goodsId.toString());
		map.put("status", "1");
		return goodsSupplierDao.getAllByGoodsId(map, false).getRows();
	}
	/*public List<GoodsSupplierInfo> getAllByGoodsId(Integer goodsId){
		Map<String,String> map=new HashMap<>();
		map.put("goodsId", goodsId.toString());
		map.put("status", "1");
		return goodsSupplierDao.getAllByGoodsId(map, false).getRows();
	}*/
	@Override
	public List<GoodsSupplierInfo> getAllBySupplierId(Integer supplierId){
		Map<String,String> map=new HashMap<>();
		map.put("supplierId", supplierId.toString());
		map.put("status", "1");
		return goodsSupplierDao.getAllBySupplierId(map, false).getRows();
	}

}
