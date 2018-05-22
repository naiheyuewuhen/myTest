package com.manage.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.manage.dao.PurchaseOrderDetailDao;
import com.manage.model.PurchaseOrderDetail;
import com.manage.util.StringUtil;

@Repository("purchaseOrderDetailDao")
public class PurchaseOrderDetailDaoImpl implements PurchaseOrderDetailDao {

//	SELECT o.id as purchase_order_id,g.goods_id,COALESCE(SUM(g.goods_num),0) as goods_total_num,1 FROM purchase_order o LEFT JOIN contract_goods g ON g.contract_id=o.contract_id AND g.supplier_id_reality=o.supplier_id WHERE g.status=1 AND o.status=3 AND g.resolve_id IN (13) GROUP BY g.goods_id;
	private static final String PURCHASE_ORDER_DETAIL_INSERT="INSERT INTO purchase_order_detail (purchase_order_id,goods_id,price,goods_total_num,status) SELECT purchase_order_id,goods_id,price,goods_total_num,1 FROM (SELECT o.id as purchase_order_id,g.goods_id,g.price,COALESCE(SUM(g.goods_num),0) as goods_total_num FROM purchase_order o LEFT JOIN contract_goods g ON g.contract_id=o.contract_id AND g.supplier_id_reality=o.supplier_id WHERE g.status=1 AND o.status=3 AND o.contract_id=:contractId AND g.resolve_id IN (:resolveIds) GROUP BY g.goods_id,g.price) as t";
	private static final String PURCHASE_ORDER_DETAIL_ALL_SELECT="SELECT pod.id,ci.id AS contract_id,ci.code AS contract_code,gi.id AS goods_id,gi.name AS goods_name,gi.code AS goods_code,gi.standard AS goodsStandard,si.id AS supplier_id,si.name AS supplier_name,pod.goods_total_num,COALESCE(SUM(gr.goods_num),0) AS goods_sum_num,pod.price FROM purchase_order_detail pod LEFT JOIN purchase_order po ON pod.purchase_order_id=po.id LEFT JOIN contract_info ci ON po.contract_id=ci.id LEFT JOIN goods_info gi ON pod.goods_id=gi.id LEFT JOIN supplier_info si ON po.supplier_id=si.id LEFT JOIN contract_goods_receiving gr ON ci.id=gr.contract_id AND si.id=gr.supplier_id AND gr.goods_id=gi.id AND gr.status=1 where 1=1 ";
	
	@Resource
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Override
	public List<PurchaseOrderDetail> getAll(Map<String,Object> map){
		StringBuilder sb = new StringBuilder();
		for(String key:map.keySet()) {
			if(!StringUtil.isBlank(map.get(key))) {
				if(!(key.equals("page")||key.equals("rows")||key.equals("sort")||key.equals("order"))) {
					if(key.equals("contractId")) {
						sb.append(" and ci.id="+map.get(key));
					}else if(key.equals("contractCode")) {
						sb.append(" and ci.code like '%"+map.get(key)+"%'");
					}else if(key.equals("goodsId")) {
						sb.append(" and gi.id="+map.get(key));
					}else if(key.equals("goodsCode")) {
						sb.append(" and gi.code like '%"+map.get(key)+"%'");
					}else if(key.equals("goodsName")) {
						sb.append(" and gi.name like '%"+map.get(key)+"%'");
					}else if(key.equals("goodsStandard")) {
						sb.append(" and gi.standard like '%"+map.get(key)+"%'");
					}else if(key.equals("supplierId")) {
						sb.append(" and si.id="+map.get(key));
					}else if(key.equals("supplierName")) {
						sb.append(" and si.name like '%"+map.get(key)+"%'");
					}else if(key.equals("purchaseOrderId")) {
						sb.append(" and po.id="+map.get(key));
					}else if(key.equals("orderNo")) {
						sb.append(" and po.order_no like '%"+map.get(key)+"%'");
					}
				}
			}
		}
		sb.append(" GROUP BY gr.contract_id,gr.goods_id,po.id,pod.price ");
		RowMapper<PurchaseOrderDetail> rowMapper = new BeanPropertyRowMapper<>(PurchaseOrderDetail.class);
		List<PurchaseOrderDetail> list = namedParameterJdbcTemplate.query(PURCHASE_ORDER_DETAIL_ALL_SELECT+sb.toString(), rowMapper);
		return list;
	}
	@Override
	public Boolean saveBatch(Integer contractId,String resolveIds) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("contractId", contractId);
		map.put("resolveIds", resolveIds);
		namedParameterJdbcTemplate.update(PURCHASE_ORDER_DETAIL_INSERT,map);
		return true;
	}
}
