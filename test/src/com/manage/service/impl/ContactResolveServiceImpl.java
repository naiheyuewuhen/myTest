package com.manage.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.manage.dao.ContactResolveDao;
import com.manage.dao.ContractDao;
import com.manage.dao.ContractGoodsDao;
import com.manage.dao.GoodsSupplierDao;
import com.manage.dao.PurchaseOrderDetailDao;
import com.manage.dao.PurchaseOrdersDao;
import com.manage.model.ContactResolve;
import com.manage.model.ContractGoods;
import com.manage.model.GoodsSupplierInfo;
import com.manage.model.WebPage;
import com.manage.service.ContactResolveService;

@Service("contactResolveService")
public class ContactResolveServiceImpl implements ContactResolveService {

	@Resource
	private ContactResolveDao contactResolveDao;
	
	@Resource
	private ContractGoodsDao contractGoodsDao;
	
	@Resource
	private GoodsSupplierDao goodsSupplierDao;
	
	@Resource
	private ContractDao contractDao;
	
	@Resource
	private PurchaseOrdersDao purchaseOrdersDao;
	@Resource
	private PurchaseOrderDetailDao purchaseOrderDetailDao;
	
	@Override
	public Boolean saveOrUpdate(ContactResolve contactResolve,List<ContractGoods> contractGoodsList) {
		//更新操作
		Integer contactResolveId = null;
		List<ContractGoods> contractGoodsListUpdate=new ArrayList<ContractGoods>();
		List<ContractGoods> contractGoodsListInsert=new ArrayList<ContractGoods>();
		if(contactResolve.getId()>0) {
			contactResolveId=contactResolve.getId();
			contactResolveDao.update(contactResolve);
		}else {
			contactResolveId=contactResolveDao.save(contactResolve);
		}
		Integer status=7;//7:拆解进行中
		if(contactResolve.getStatus()==1) {//此条拆解完成
			Map<String,String> map=new HashMap<String,String>();
			map.put("contractId", contactResolve.getContractId().toString());
			map.put("status", "2");//2-未拆解完成
			List<ContactResolve> resolveList=contactResolveDao.getAll(map, false).getRows();
			if(resolveList.isEmpty()) {
				status=8;//8:合同拆解完成
			}
		}
		//更新合同的状态
		contractDao.updateStatus(contactResolve.getContractId(), status);
		for(ContractGoods contractGoods : contractGoodsList) {
			contractGoods.setResolveId(contactResolveId);
			if(contractGoods.getId()>0) {
				contractGoodsListUpdate.add(contractGoods);
			}else {
				contractGoodsListInsert.add(contractGoods);
			}
		}
		
		//先更新所关联的记录为无效，后续根据条件更新为有效或插入新的记录
		contractGoodsDao.updateUnuse(contactResolveId);
		contractGoodsDao.updateForResolveBatch(contractGoodsListUpdate);
		contractGoodsDao.saveBatch(contractGoodsListInsert);
		return true;
	}
	@Override
	public Boolean saveOrUpdateBatch(Integer contractId,List<ContactResolve> contactResolveList) {
		//更新操作
		List<ContactResolve> listUpdate=new ArrayList<ContactResolve>();
		List<ContactResolve> listInsert=new ArrayList<ContactResolve>();
		for(ContactResolve contactResolve : contactResolveList) {
			if(contactResolve.getId()>0) {
				listUpdate.add(contactResolve);
			}else {
				listInsert.add(contactResolve);
			}
		}
		
		//先更新所关联的记录为无效，后续根据条件更新为有效或插入新的记录
		contactResolveDao.updateUnuse(contractId);
		contactResolveDao.updateBatch(listUpdate);
		contactResolveDao.saveBatch(listInsert);
		return true;
	}

	@Override
	public WebPage<ContactResolve> getAll(Map<String, String> map, Boolean hasPage) {
		return contactResolveDao.getAll(map,hasPage);
	}

	@Override
	public ContactResolve getById(Integer id) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("resolveId", id);
		ContactResolve contactResolve = contactResolveDao.getById(id);
		List<ContractGoods> contractGoodsList = contractGoodsDao.getAll(map,false).getRows();
		contactResolve.setContractGoodsList(contractGoodsList);
		return contactResolve;
	}
	@Override
	public Integer updateStatus(ContactResolve contactResolve) {
		return contactResolveDao.updateStatus(contactResolve);
	}
	@Override
	public boolean updateForReality(ContactResolve contactResolve,List<ContractGoods> contractGoodsList) {
		contactResolveDao.updateStatus(contactResolve);
		contractGoodsDao.updateForRealityBatch(contractGoodsList);
		Integer status=9;//9:供应商确认中
		if(contactResolve.getStatus()==4) {//4：此条供应商确认完成
			Map<String,String> map=new HashMap<String,String>();
			map.put("contractId", contactResolve.getContractId().toString());
			map.put("statusArr", "1,2,3");//1：有效（拆解完成）；2：暂存（未生效）；3：供应商确认中；4：供应商确认完成',
			List<ContactResolve> resolveList=contactResolveDao.getAll(map, false).getRows();
			if(resolveList.isEmpty()) {
				status=10;//10:供应商确认完成
			}
		}
		//更新合同的状态
		contractDao.updateStatus(contactResolve.getContractId(), status);
		List<GoodsSupplierInfo> goodsSupplierInfoList = new ArrayList<GoodsSupplierInfo>();
		for(ContractGoods contractGoods : contractGoodsList) {
			GoodsSupplierInfo goodsSupplierInfo = new GoodsSupplierInfo();
			goodsSupplierInfo.setGoodsId(contractGoods.getGoodsId());
			goodsSupplierInfo.setSupplierId(contractGoods.getSupplierIdReality());
			goodsSupplierInfo.setPrice(contractGoods.getPrice());
			goodsSupplierInfoList.add(goodsSupplierInfo);
		}
		goodsSupplierDao.updateByGoodsAndSupplierBatch(goodsSupplierInfoList);
		return true;
	}
	
	@Override
	public Boolean saveToPurchaseOrders(Integer contractId,String orderNo,String resolveIds,String createUser,Date createTime) {
		purchaseOrdersDao.saveBatch(contractId, orderNo, resolveIds, createUser, createTime);
		purchaseOrderDetailDao.saveBatch(contractId,resolveIds);
		purchaseOrdersDao.updateTempToUse(contractId);
		contactResolveDao.updateStatusByIds(resolveIds,5);//5:已生成采购单
		return true;
	}


	@Override
	public Boolean getExist(Map<String, String> map) {
		return contactResolveDao.getExist(map);
	}

}
