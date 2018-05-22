package com.manage.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.manage.dao.SupplierDao;
import com.manage.model.SupplierInfo;
import com.manage.model.WebPage;
import com.manage.util.StringUtil;

@Repository("supplierDao")
public class SupplierDaoImpl implements SupplierDao {
	private static final String SUPPLIER_BY_ID_SELECT="select * from supplier_info where id = :id ";
	private static final String SUPPLIER_ALL_SELECT="select * from supplier_info where 1=1 ";
	private static final String SUPPLIER_ALL_IN_CONTRACT_SELECT="select * from supplier_info where id in (SELECT DISTINCT supplier_id_reality FROM contract_goods WHERE contract_id=:contractId)";
	private static final String SUPPLIER_ALL_COUNT_SELECT="select COUNT(1) count from supplier_info where 1=1 ";
	private static final String SUPPLIER_INSERT="insert into supplier_info(name,phone,address,create_time,remark,sort_num) values (:name,:phone,:address,:createTime,:remark,:sortNum)";
	private static final String SUPPLIER_UPDATE="update supplier_info set name=:name,phone=:phone,address=:address,remark=:remark,sort_num=:sortNum where id=:id";
	
	@Resource
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Override
	public SupplierInfo getById(Integer id) {
		Map<String,Integer> paramMap=new HashMap<String,Integer>();
		paramMap.put("id", id);
		RowMapper<SupplierInfo> rowMapper = new BeanPropertyRowMapper<>(SupplierInfo.class);
		List<SupplierInfo> supplierInfoList = namedParameterJdbcTemplate.query(SUPPLIER_BY_ID_SELECT, paramMap, rowMapper);
		if(supplierInfoList.size()>0) {
			return supplierInfoList.get(0);
		}
		return null;
	}
	@Override
	public List<SupplierInfo> getAllInContractByContractId(Integer contractId,Map<String,String> map) {
		StringBuilder sb = new StringBuilder();
		for(String key:map.keySet()) {
			if(key.equals("supplierName")) {
				sb.append(" and name like '%"+map.get(key)+"%'");
			}
		}
		Map<String,Integer> paramMap=new HashMap<String,Integer>();
		paramMap.put("contractId", contractId);
		RowMapper<SupplierInfo> rowMapper = new BeanPropertyRowMapper<>(SupplierInfo.class);
		List<SupplierInfo> supplierInfoList = namedParameterJdbcTemplate.query(SUPPLIER_ALL_IN_CONTRACT_SELECT+sb.toString(), paramMap, rowMapper);
		return supplierInfoList;
	}
	
	@Override
	public WebPage<SupplierInfo> getAll(Map<String,String> map,Boolean hasPage){
		StringBuilder sb = new StringBuilder();
		Integer page = (map.get("page")==null)? 1: Integer.valueOf(map.get("page"));
		Integer rows = (map.get("rows")==null)? 10: Integer.valueOf(map.get("rows"));
		String sort = (map.get("sort")==null)? "sortNum": map.get("sort");
		String order = (map.get("order")==null)? "desc": map.get("order");
		Long total = 0L;
		for(String key:map.keySet()) {
			if(!map.get(key).isEmpty()) {
				if(!(key.equals("page")||key.equals("rows")||key.equals("sort")||key.equals("order"))) {
					sb.append(" and "+StringUtil.propertyToField(key));
					if(key.equals("id")||key.equals("status")) {
						sb.append(" = "+map.get(key));
					}else {
						sb.append(" like '%"+map.get(key)+"%'");
					}
				}
			}
		}
		sb.append(" order by " + StringUtil.propertyToField(sort) + " " + order + ",id asc");
		if(hasPage) {
			total = namedParameterJdbcTemplate.getJdbcOperations().queryForObject(SUPPLIER_ALL_COUNT_SELECT+sb.toString(), Long.class);
			sb.append(" limit "+(page-1)*rows+" , " + rows);
		}
		WebPage<SupplierInfo> webPage=new WebPage<>(total, page);
		RowMapper<SupplierInfo> rowMapper = new BeanPropertyRowMapper<>(SupplierInfo.class);
		List<SupplierInfo> supplierInfoList = namedParameterJdbcTemplate.query(SUPPLIER_ALL_SELECT+sb.toString(), rowMapper);
		webPage.setRows(supplierInfoList);
		return webPage;
	}
	@Override
	public List<SupplierInfo> getAllInIds(String ids){
		StringBuilder sb = new StringBuilder();
		sb.append(" and id in (").append(ids).append(") order by sort_num desc,id asc");
		RowMapper<SupplierInfo> rowMapper = new BeanPropertyRowMapper<>(SupplierInfo.class);
		List<SupplierInfo> supplierInfoList = namedParameterJdbcTemplate.query(SUPPLIER_ALL_SELECT+sb.toString(), rowMapper);
		return supplierInfoList;
	}
	@Override
	public Integer save(SupplierInfo supplierInfo) {
		SqlParameterSource paramMap = new BeanPropertySqlParameterSource(supplierInfo);
		KeyHolder keyHolder= new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update(SUPPLIER_INSERT, paramMap,keyHolder);
		Integer id = keyHolder.getKey().intValue();//获得主键值
		return id;
	}

	@Override
	public Integer update(SupplierInfo supplierInfo) {
		SqlParameterSource paramMap = new BeanPropertySqlParameterSource(supplierInfo);
		return namedParameterJdbcTemplate.update(SUPPLIER_UPDATE, paramMap);
	}
	
	@Override
	public Boolean getExist(Map<String,String> map) {
		StringBuilder sb = new StringBuilder();
		for(String key:map.keySet()) {
			if(!map.get(key).isEmpty()) {
				sb.append(" and "+StringUtil.propertyToField(key));
				if(key.equals("id")) {
					sb.append(" <> "+map.get(key));
				}else if(key.equals("status")) {
					sb.append(" = "+map.get(key));
				}else {
					sb.append(" = '"+map.get(key)+"'");
				}
			}
		}
		RowMapper<SupplierInfo> rowMapper = new BeanPropertyRowMapper<>(SupplierInfo.class);
		List<SupplierInfo> supplierInfoList = namedParameterJdbcTemplate.query(SUPPLIER_ALL_SELECT+sb.toString(), rowMapper);
		if(supplierInfoList.isEmpty()) {
			return false;
		}else {
			return true;
		}
	}
}
