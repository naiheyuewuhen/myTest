package com.manage.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;

import com.manage.dao.GoodsReceivingDao;
import com.manage.model.GoodsReceiving;
import com.manage.model.WebPage;
import com.manage.util.StringUtil;

@Repository("goodsReceivingDao")
public class GoodsReceivingDaoImpl implements GoodsReceivingDao {

	private static final String GOODS_RECEIVING_BY_ID_SELECT="select * from contract_goods_receiving where id = :id ";
	private static final String GOODS_RECEIVING_ALL_SELECT="SELECT r.*,ci.code AS contract_code,gi.code AS goods_code,gi.name AS goods_name,gi.standard AS goods_standard,cg.price FROM contract_goods_receiving r LEFT JOIN contract_info ci ON r.contract_id=ci.id LEFT JOIN goods_info gi ON r.goods_id=gi.id LEFT JOIN contract_goods cg ON r.goods_id=cg.goods_id AND r.contract_id=cg.contract_id LEFT JOIN supplier_info si ON r.supplier_id=si.id where r.status=1 ";
	private static final String GOODS_RECEIVING_ALL_COUNT_SELECT="select COUNT(1) count FROM contract_goods_receiving r LEFT JOIN contract_info ci ON r.contract_id=ci.id LEFT JOIN goods_info gi ON r.goods_id=gi.id LEFT JOIN contract_goods cg ON r.goods_id=cg.goods_id AND r.contract_id=cg.contract_id LEFT JOIN supplier_info si ON r.supplier_id=si.id where r.status=1 ";
	private static final String CONTRACT_GOODS_RECEIVING_ALL_SELECT="SELECT cg.id,ci.id AS contract_id,ci.code AS contract_code,gi.id AS goods_id,gi.name AS goods_name,gi.code AS goods_code,gi.standard AS goodsStandard,si.id AS supplier_id,si.name AS supplier_name,COALESCE(SUM(cg.goods_num),0) AS goods_total_num,COALESCE(SUM(gr.goods_num),0) AS goods_sum_num,cg.price FROM contract_goods cg LEFT JOIN contract_info ci ON cg.contract_id=ci.id LEFT JOIN goods_info gi ON cg.goods_id=gi.id LEFT JOIN supplier_info si ON cg.supplier_id_reality=si.id LEFT JOIN contract_goods_receiving gr ON ci.id=gr.contract_id AND si.id=gr.supplier_id AND gr.goods_id=gi.id AND gr.status=1 where 1=1 ";
	private static final String GOODS_RECEIVING_INSERT="insert into contract_goods_receiving(contract_id,goods_id,goods_num,supplier_id,purchase_order_id,create_user,create_time) values (:contractId,:goodsId,:goodsNum,:supplierId,:purchaseOrderId,:createUser,:createTime)";
//	private static final String CONTRACT_GOODS_UPDATE="update contract_goods set status=:status where id=:id";
	@Resource
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Override
	public GoodsReceiving getById(Integer id) {
		Map<String,Integer> paramMap=new HashMap<String,Integer>();
		paramMap.put("id", id);
		RowMapper<GoodsReceiving> rowMapper = new BeanPropertyRowMapper<>(GoodsReceiving.class);
		List<GoodsReceiving> goodsReceivingList = namedParameterJdbcTemplate.query(GOODS_RECEIVING_BY_ID_SELECT, paramMap, rowMapper);
		if(goodsReceivingList.size()>0) {
			return goodsReceivingList.get(0);
		}
		return null;
	}
	@Override
	public WebPage<GoodsReceiving> getAll(Map<String,Object> map,Boolean hasPage){
		StringBuilder sb = new StringBuilder();
		Integer page = (map.get("page")==null)? 1: Integer.valueOf(map.get("page").toString());
		Integer rows = (map.get("rows")==null)? 10: Integer.valueOf(map.get("rows").toString());
		String sort = (map.get("sort")==null)? "id": map.get("sort").toString();
		String order = (map.get("order")==null)? "desc": map.get("order").toString();
		Long total = 0L;
		for(String key:map.keySet()) {
			if(!StringUtil.isBlank(map.get(key))) {
				if(!(key.equals("page")||key.equals("rows")||key.equals("sort")||key.equals("order"))) {
					if(map.get(key) instanceof Integer) {
						sb.append(" and r."+StringUtil.propertyToField(key));
						sb.append(" = "+map.get(key));
					}else {
						if(key.indexOf("TimeStart")>0) {
							sb.append(" and r."+StringUtil.propertyToField(key.substring(0,key.indexOf("TimeStart"))+"Time"));
							sb.append(" >='"+map.get(key)+"'");
						}else if(key.indexOf("TimeEnd")>0) {
							sb.append(" and r."+StringUtil.propertyToField(key.substring(0,key.indexOf("TimeEnd"))+"Time"));
							sb.append(" <='"+map.get(key)+"'");
						}else {
							sb.append(" like '%"+map.get(key)+"%'");
						}
					}
				}
			}
		}
		sb.append(" order by r." + StringUtil.propertyToField(sort) + " " + order);
		if(hasPage) {
			total = namedParameterJdbcTemplate.getJdbcOperations().queryForObject(GOODS_RECEIVING_ALL_COUNT_SELECT+sb.toString(), Long.class);
			sb.append(" limit "+(page-1)*rows+" , " + rows);
		}
		WebPage<GoodsReceiving> webPage=new WebPage<>(total, page);
		RowMapper<GoodsReceiving> rowMapper = new BeanPropertyRowMapper<>(GoodsReceiving.class);
		List<GoodsReceiving> goodsReceivingList = namedParameterJdbcTemplate.query(GOODS_RECEIVING_ALL_SELECT+sb.toString(), rowMapper);
		webPage.setRows(goodsReceivingList);
		return webPage;
	}
	@Override
	public List<GoodsReceiving> getAllForContract(Map<String,Object> map){
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
					}
				}
			}
		}
		sb.append(" GROUP BY gr.contract_id,gr.goods_id,gr.supplier_id,cg.price ");
		RowMapper<GoodsReceiving> rowMapper = new BeanPropertyRowMapper<>(GoodsReceiving.class);
		List<GoodsReceiving> goodsReceivingList = namedParameterJdbcTemplate.query(CONTRACT_GOODS_RECEIVING_ALL_SELECT+sb.toString(), rowMapper);
		return goodsReceivingList;
	}
	@Override
	public Boolean saveBatch(List<GoodsReceiving> goodsReceivingList) {
		if(goodsReceivingList.size()>0) {
			namedParameterJdbcTemplate.batchUpdate(GOODS_RECEIVING_INSERT, SqlParameterSourceUtils.createBatch(goodsReceivingList.toArray()));
		}
		return true;
	}
}
