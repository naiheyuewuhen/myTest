package com.manage.service;

import java.util.List;
import java.util.Map;

import com.manage.model.GoodsSupplierInfo;

public interface GoodsSupplierService {

//	List<GoodsSupplierInfo> getAllByGoodsId(Integer goodsId);

	List<GoodsSupplierInfo> getAllBySupplierId(Integer supplierId);

	List<GoodsSupplierInfo> getAllByGoodsId(Map<String, String> map);

}