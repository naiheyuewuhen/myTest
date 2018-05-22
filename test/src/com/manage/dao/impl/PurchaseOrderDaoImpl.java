package com.manage.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.manage.dao.PurchaseOrdersDao;
import com.manage.model.PurchaseOrder;
import com.manage.model.WebPage;
import com.manage.util.StringUtil;

@Repository("purchaseOrderDao")
public class PurchaseOrderDaoImpl implements PurchaseOrdersDao {
	//INSERT INTO purchase_order (contract_id,order_no,supplier_id,create_user,create_time,status) SELECT 30,CONCAT('order',supplier_id_reality) as order_no,supplier_id_reality, 'sql','2018-01-02 02:02:20',1 FROM (SELECT DISTINCT supplier_id_reality FROM contract_goods where contract_id=30 AND supplier_id_reality is not null) as t
//	SELECT DISTINCT supplier_id_reality FROM contact_resolve r LEFT JOIN contract_goods g on r.id=g.resolve_id where r.contract_id=30 and r.status=4
	private static final String PURCHASE_ORDER_BY_ID_SELECT="select * from purchase_order where id = :id ";
	private static final String PURCHASE_ORDER_INSERT="INSERT INTO purchase_order (contract_id,order_no,supplier_id,create_user,create_time,status) SELECT :contractId,CONCAT(:orderNo,supplier_id_reality) as order_no,supplier_id_reality, :createUser,:createTime,3 FROM (SELECT DISTINCT g.supplier_id_reality FROM contact_resolve r LEFT JOIN contract_goods g on r.id=g.resolve_id where r.contract_id=:contractId and r.status=4 AND r.id in (:resolveIds) AND g.supplier_id_reality is not null) as t";
	private static final String PURCHASE_ORDER_UPDATE_UNUSE="update purchase_order set status=0 where contract_id=:contractId";
	private static final String PURCHASE_ORDER_UPDATE_TEMP_TO_USE="update purchase_order set status=1 where contract_id=:contractId and status=3";
	private static final String PURCHASE_ORDER_ALL_SELECT="select o.*,ci.code as contractCode from purchase_order o left join contract_info ci on o.contract_id=ci.id left join supplier_info si on si.id=o.supplier_id where 1=1 ";
	private static final String PURCHASE_ORDER_ALL_COUNT_SELECT="select COUNT(1) count from purchase_order o left join contract_info ci on o.contract_id=ci.id left join supplier_info si on si.id=o.supplier_id where 1=1 ";
	
	@Resource
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Override
	public PurchaseOrder getById(Integer id) {
		Map<String,Integer> paramMap=new HashMap<String,Integer>();
		paramMap.put("id", id);
		RowMapper<PurchaseOrder> rowMapper = new BeanPropertyRowMapper<>(PurchaseOrder.class);
		List<PurchaseOrder> list = namedParameterJdbcTemplate.query(PURCHASE_ORDER_BY_ID_SELECT, paramMap, rowMapper);
		if(list.size()>0) {
			return list.get(0);
		}
		return null;
	}
	@Override
	public WebPage<PurchaseOrder> getAll(Map<String,String> map,Boolean hasPage){
		StringBuilder sb = new StringBuilder();
		Integer page = (map.get("page")==null)? 1: Integer.valueOf(map.get("page").toString());
		Integer rows = (map.get("rows")==null)? 10: Integer.valueOf(map.get("rows").toString());
		String sort = (map.get("sort")==null)? "id": map.get("sort").toString();
		String order = (map.get("order")==null)? "desc": map.get("order").toString();
		Long total = 0L;
		for(String key:map.keySet()) {
			if(!StringUtil.isBlank(map.get(key))) {
				if(!(key.equals("page")||key.equals("rows")||key.equals("sort")||key.equals("order"))) {
					if(key.equals("id")||key.equals("contractId")||key.equals("supplierId")||key.equals("status")) {
						sb.append(" and o."+StringUtil.propertyToField(key));
						sb.append(" = "+map.get(key));
					}else if(key.equals("contractCode")){
						sb.append(" and ci.code");
						sb.append(" like '%"+map.get(key)+"%'");
					}else if(key.equals("supplierName")){
						sb.append(" and si.name");
						sb.append(" like '%"+map.get(key)+"%'");
					}
				}
			}
		}
		sb.append(" order by o." + StringUtil.propertyToField(sort) + " " + order);
		if(hasPage) {
			total = namedParameterJdbcTemplate.getJdbcOperations().queryForObject(PURCHASE_ORDER_ALL_COUNT_SELECT+sb.toString(), Long.class);
			sb.append(" limit "+(page-1)*rows+" , " + rows);
		}
		WebPage<PurchaseOrder> webPage=new WebPage<>(total, page);
		RowMapper<PurchaseOrder> rowMapper = new BeanPropertyRowMapper<>(PurchaseOrder.class);
		List<PurchaseOrder> goodsReceivingList = namedParameterJdbcTemplate.query(PURCHASE_ORDER_ALL_SELECT+sb.toString(), rowMapper);
		webPage.setRows(goodsReceivingList);
		return webPage;
	}
	@Override
	public Boolean saveBatch(Integer contractId,String orderNo,String resolveIds,String createUser,Date createTime) {
		Map<String,Object> paramMap=new HashMap<String,Object>();
		paramMap.put("contractId", contractId);
		paramMap.put("orderNo", orderNo);
		paramMap.put("resolveIds", resolveIds);
		paramMap.put("createUser", createUser);
		paramMap.put("createTime", createTime);
		namedParameterJdbcTemplate.update(PURCHASE_ORDER_INSERT, paramMap);
		return true;
	}
	@Override
	public Integer updateUnuse(Integer contractId) {
		Map<String,Integer> map=new HashMap<String,Integer>();
		map.put("contractId", contractId);
		return namedParameterJdbcTemplate.update(PURCHASE_ORDER_UPDATE_UNUSE, map);
	}
	@Override
	public Integer updateTempToUse(Integer contractId) {
		Map<String,Integer> map=new HashMap<String,Integer>();
		map.put("contractId", contractId);
		return namedParameterJdbcTemplate.update(PURCHASE_ORDER_UPDATE_TEMP_TO_USE,map);
	}
}
