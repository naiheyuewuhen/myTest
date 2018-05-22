package com.manage.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.manage.dao.GoodsSupplierDao;
import com.manage.dao.SupplierDao;
import com.manage.model.GoodsSupplierInfo;
import com.manage.model.SupplierInfo;
import com.manage.model.WebPage;
import com.manage.service.SupplierService;

@Service("supplierService")
public class SupplierServiceImpl implements SupplierService {

	@Resource
	private SupplierDao supplierDao;
	@Resource
	private GoodsSupplierDao goodsSupplierDao;
	@Override
	public Boolean saveOrUpdate(SupplierInfo supplierInfo) {
		Integer supplierId = null;
		if(supplierInfo.getId()>0) {
			supplierId=supplierInfo.getId();
			supplierDao.update(supplierInfo);
		}else {
			supplierId=supplierDao.save(supplierInfo);
		}
		List<GoodsSupplierInfo> goodsSupplierListUpdate=new ArrayList<GoodsSupplierInfo>();
		List<GoodsSupplierInfo> goodsSupplierListInsert=new ArrayList<GoodsSupplierInfo>();
		List<GoodsSupplierInfo> goodsSupplierInfoList=supplierInfo.getGoodsSupplierInfoList();
		for(GoodsSupplierInfo goodsSupplierInfo : goodsSupplierInfoList) {
			goodsSupplierInfo.setSupplierId(supplierId);
			goodsSupplierInfo.setStatus(1);
			if(goodsSupplierInfo.getId()>0) {
				goodsSupplierListUpdate.add(goodsSupplierInfo);
			}else {
				goodsSupplierListInsert.add(goodsSupplierInfo);
			}
		}
		goodsSupplierDao.updateUnuse(supplierId);
		goodsSupplierDao.updateBatch(goodsSupplierListUpdate);
		goodsSupplierDao.saveBatch(goodsSupplierListInsert);
		return true;
	}

	@Override
	public WebPage<SupplierInfo> getAll(Map<String, String> map, Boolean hasPage) {
		return supplierDao.getAll(map, hasPage);
	}

	@Override
	public SupplierInfo getById(Integer id,Boolean onlyValidGoodsSupplier) {
		SupplierInfo supplierInfo=supplierDao.getById(id);
		if(supplierInfo!=null) {
			Map<String,String> map=new HashMap<>();
			map.put("supplierId", id.toString());
			if(onlyValidGoodsSupplier) {
				map.put("status", "1");
			}
			supplierInfo.setGoodsSupplierInfoList(goodsSupplierDao.getAllBySupplierId(map, false).getRows());
		}
		return supplierInfo;
	}
	@Override
	public List<SupplierInfo> getAllInContractByContractId(Integer contractId,Map<String,String> map) {
		return supplierDao.getAllInContractByContractId(contractId,map);
	}

	@Override
	public Boolean getExist(Map<String, String> map) {
		return supplierDao.getExist(map);
	}

	@Override
	public List<SupplierInfo> getAllInIds(String ids) {
		return supplierDao.getAllInIds(ids);
	}

}
