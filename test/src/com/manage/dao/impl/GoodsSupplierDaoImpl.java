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

import com.manage.dao.GoodsSupplierDao;
import com.manage.model.GoodsSupplierInfo;
import com.manage.model.WebPage;
import com.manage.util.StringUtil;

@Repository("goodsSupplierDao")
public class GoodsSupplierDaoImpl implements GoodsSupplierDao{
	private static final String GOODS_SUPPLIER_INSERT="insert into goods_supplier(goods_id,supplier_id,price) values (:goodsId,:supplierId,:price)";
	private static final String GOODS_SUPPLIER_UPDATE_UNUSE="update goods_supplier set status=0 where supplier_id=:supplierId";
	private static final String GOODS_SUPPLIER_UPDATE="update goods_supplier set status=:status,price=:price where id=:id";
	private static final String GOODS_SUPPLIER_UPDATE_BY_GOODS_AND_SUPPLIER="update goods_supplier set price=:price where goods_id=:goodsId and supplier_id=:supplierId";
	private static final String GOODS_SUPPLIER_BY_SUPPLIER_ID_SELECT="select g.code as goodsCode,g.name as goodsName,g.standard as goodsStandard,g.create_time as goodsCreateTime,g.remark as goodsRemark, g.sort_num as goodsSortNum,g.status as goodsStatus,s.* from goods_supplier s left join goods_info g on s.goods_id=g.id where g.status=1 ";
	private static final String GOODS_SUPPLIER_BY_SUPPLIER_ID_COUNT_SELECT="select COUNT(1) from goods_supplier s left join goods_info g on s.goods_id=g.id where g.status=1 ";
	private static final String GOODS_SUPPLIER_BY_GOODS_ID_SELECT="select s.name as supplierName,s.phone as supplierPhone,s.address as supplierAddress,s.create_time as supplierCreateTime,s.remark as supplierRemark,s.sort_num as supplierSortNum,s.status as supplierStatus,g.* from goods_supplier g left join supplier_info s on g.supplier_id=s.id where s.status=1 ";
	private static final String GOODS_SUPPLIER_BY_GOODS_ID_COUNT_SELECT="select COUNT(1) from goods_supplier g left join supplier_info s on g.supplier_id=s.id where s.status=1 ";
	
	@Resource
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	@Override
	public Boolean saveBatch(List<GoodsSupplierInfo> goodsSupplierInfoList) {
		if(goodsSupplierInfoList.size()>0) {
			namedParameterJdbcTemplate.batchUpdate(GOODS_SUPPLIER_INSERT, SqlParameterSourceUtils.createBatch(goodsSupplierInfoList.toArray()));
		}
		return true;
	}
	@Override
	public Integer updateUnuse(Integer supplierId) {
		Map<String,Integer> map=new HashMap<String,Integer>();
		map.put("supplierId", supplierId);
		return namedParameterJdbcTemplate.update(GOODS_SUPPLIER_UPDATE_UNUSE, map);
	}
	@Override
	public Boolean updateBatch(List<GoodsSupplierInfo> goodsSupplierInfoList) {
		if(goodsSupplierInfoList.size()>0) {
			namedParameterJdbcTemplate.batchUpdate(GOODS_SUPPLIER_UPDATE, SqlParameterSourceUtils.createBatch(goodsSupplierInfoList.toArray()));
		}
		return true;
	}
	@Override
	public Boolean updateByGoodsAndSupplierBatch(List<GoodsSupplierInfo> goodsSupplierInfoList) {
		if(goodsSupplierInfoList.size()>0) {
			namedParameterJdbcTemplate.batchUpdate(GOODS_SUPPLIER_UPDATE_BY_GOODS_AND_SUPPLIER, SqlParameterSourceUtils.createBatch(goodsSupplierInfoList.toArray()));
		}
		return true;
	}
	
	@Override
	public WebPage<GoodsSupplierInfo> getAllBySupplierId(Map<String,String> map,Boolean hasPage){
		StringBuilder sb = new StringBuilder();
		Integer page = (map.get("page")==null)? 1: Integer.valueOf(map.get("page"));
		Integer rows = (map.get("rows")==null)? 10: Integer.valueOf(map.get("rows"));
		String sort = (map.get("sort")==null)? "sortNum": map.get("sort");
		String order = (map.get("order")==null)? "desc": map.get("order");
		Long total = 0L;
		for(String key:map.keySet()) {
			if(!map.get(key).isEmpty()) {
				if(!(key.equals("page")||key.equals("rows")||key.equals("sort")||key.equals("order"))) {
					if(key.equals("id")||key.equals("status")||key.equals("supplierId")) {
						sb.append(" and s."+StringUtil.propertyToField(key));
						sb.append(" = ");
						sb.append(map.get(key));
					}else {
						sb.append(" and g."+StringUtil.propertyToField(key));
//						if(key.equals("goodsStatus")){
//							sb.append(" = ");
//							sb.append(map.get(key));
//						}else {
							sb.append(" like '%");
							sb.append(map.get(key));
							sb.append("%'");
//						}
					}
				}
			}
		}
		sb.append(" order by g." + StringUtil.propertyToField(sort) + " " + order + ",g.id asc");
		if(hasPage) {
			total = namedParameterJdbcTemplate.getJdbcOperations().queryForObject(GOODS_SUPPLIER_BY_SUPPLIER_ID_COUNT_SELECT+sb.toString(), Long.class);
			sb.append(" limit "+(page-1)*rows+" , " + rows);
		}
		WebPage<GoodsSupplierInfo> webPage=new WebPage<>(total, page);
		RowMapper<GoodsSupplierInfo> rowMapper = new BeanPropertyRowMapper<>(GoodsSupplierInfo.class);
		List<GoodsSupplierInfo> goodsInfoList = namedParameterJdbcTemplate.query(GOODS_SUPPLIER_BY_SUPPLIER_ID_SELECT+sb.toString(), rowMapper);
		webPage.setRows(goodsInfoList);
		return webPage;
	}
	
	@Override
	public WebPage<GoodsSupplierInfo> getAllByGoodsId(Map<String,String> map,Boolean hasPage){
		StringBuilder sb = new StringBuilder();
		Integer page = (map.get("page")==null)? 1: Integer.valueOf(map.get("page"));
		Integer rows = (map.get("rows")==null)? 10: Integer.valueOf(map.get("rows"));
		String sort = (map.get("sort")==null)? "sortNum": map.get("sort");
		String order = (map.get("order")==null)? "desc": map.get("order");
		Long total = 0L;
		for(String key:map.keySet()) {
			if(!map.get(key).isEmpty()) {
				if(!(key.equals("page")||key.equals("rows")||key.equals("sort")||key.equals("order"))) {
					if(key.equals("id")||key.equals("status")||key.equals("goodsId")) {
						sb.append(" and g."+StringUtil.propertyToField(key));
						sb.append(" = ");
						sb.append(map.get(key));
					}else if(key.equals("supplierIds")) {
						sb.append(" and s.id in (");
						sb.append(map.get(key));
						sb.append(")");
					}else {
						sb.append(" and s."+StringUtil.propertyToField(key));
//						if(key.equals("supplierStatus")){
//							sb.append(" = ");
//							sb.append(map.get(key));
//						}else {
							sb.append(" like '%");
							sb.append(map.get(key));
							sb.append("%'");
//						}
						
					}
				}
			}
		}
		sb.append(" order by s." + StringUtil.propertyToField(sort) + " " + order + ",s.id asc");
		if(hasPage) {
			total = namedParameterJdbcTemplate.getJdbcOperations().queryForObject(GOODS_SUPPLIER_BY_GOODS_ID_COUNT_SELECT+sb.toString(), Long.class);
			sb.append(" limit "+(page-1)*rows+" , " + rows);
		}
		WebPage<GoodsSupplierInfo> webPage=new WebPage<>(total, page);
		RowMapper<GoodsSupplierInfo> rowMapper = new BeanPropertyRowMapper<>(GoodsSupplierInfo.class);
		List<GoodsSupplierInfo> goodsInfoList = namedParameterJdbcTemplate.query(GOODS_SUPPLIER_BY_GOODS_ID_SELECT+sb.toString(), rowMapper);
		webPage.setRows(goodsInfoList);
		return webPage;
	}
}
