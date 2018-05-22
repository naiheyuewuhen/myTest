package com.manage.dao;

import java.util.List;
import java.util.Map;

import com.manage.model.GoodsSupplierInfo;
import com.manage.model.WebPage;

public interface GoodsSupplierDao {

	Boolean saveBatch(List<GoodsSupplierInfo> goodsSupplierInfoList);

	Integer updateUnuse(Integer supplierId);

	Boolean updateBatch(List<GoodsSupplierInfo> goodsSupplierInfoList);

	WebPage<GoodsSupplierInfo> getAllBySupplierId(Map<String, String> map, Boolean hasPage);

	WebPage<GoodsSupplierInfo> getAllByGoodsId(Map<String, String> map, Boolean hasPage);

	Boolean updateByGoodsAndSupplierBatch(List<GoodsSupplierInfo> goodsSupplierInfoList);

}
