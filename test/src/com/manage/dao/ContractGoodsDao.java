package com.manage.dao;

import java.util.List;
import java.util.Map;

import com.manage.model.ContractGoods;
import com.manage.model.WebPage;

public interface ContractGoodsDao {

	ContractGoods getById(Integer id);

	WebPage<ContractGoods> getAll(Map<String, Object> map, Boolean hasPage);

	Integer save(ContractGoods contractGoods);

	Boolean getExist(Map<String, Object> map);

	Integer updateUnuse(Integer resolveId);

	Boolean saveBatch(List<ContractGoods> contractGoodsList);

	Boolean updateForResolveBatch(List<ContractGoods> contractGoodsList);

	Boolean updateForRealityBatch(List<ContractGoods> contractGoodsList);

}
