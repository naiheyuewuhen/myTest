package com.manage.dao.impl;

import java.util.Date;
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

import com.manage.dao.ContractPayStandardDao;
import com.manage.model.ContractPayStandard;
import com.manage.model.WebPage;
import com.manage.util.StringUtil;

@Repository("contractPayStandardDao")
public class ContractPayStandardDaoImpl implements ContractPayStandardDao{
	private static final String PAY_BY_ID_SELECT="select * from contract_pay_standard where id = :id ";
	private static final String PAY_BY_NOW_SELECT="select * from contract_pay_standard where start_date <= :now and end_date >= :now";
	private static final String PAY_ALL_SELECT="select * from contract_pay_standard where 1=1 ";
	private static final String PAY_ALL_COUNT_SELECT="select COUNT(1) count from contract_pay_standard where 1=1 ";
	private static final String PAY_INSERT="insert into contract_pay_standard(start_date,end_date,pay_no1,pay_no2,pay_no3,pay_no4,pay_no5) values (:startDate,:endDate,:payNo1,:payNo2,:payNo3,:payNo4,:payNo5)";
	private static final String PAY_UPDATE="update contract_pay_standard set start_date=:startDate,end_date=:endDate,pay_no1=:payNo1,pay_no2=:payNo2,pay_no3=:payNo3,pay_no4=:payNo4,pay_no5=:payNo5 where id=:id";
	
	
	
	@Resource
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Override
	public ContractPayStandard getById(Integer id) {
		Map<String,Integer> paramMap=new HashMap<String,Integer>();
		paramMap.put("id", id);
		RowMapper<ContractPayStandard> rowMapper = new BeanPropertyRowMapper<>(ContractPayStandard.class);
		List<ContractPayStandard> contractPayStandardList = namedParameterJdbcTemplate.query(PAY_BY_ID_SELECT, paramMap, rowMapper);
		if(contractPayStandardList.size()>0) {
			return contractPayStandardList.get(0);
		}
		return null;
	}
	@Override
	public ContractPayStandard getByNow(Date now) {
		Map<String,Date> paramMap=new HashMap<String,Date>();
		paramMap.put("now", now);
		RowMapper<ContractPayStandard> rowMapper = new BeanPropertyRowMapper<>(ContractPayStandard.class);
		List<ContractPayStandard> contractPayStandardList = namedParameterJdbcTemplate.query(PAY_BY_NOW_SELECT, paramMap, rowMapper);
		if(contractPayStandardList.size()>0) {
			return contractPayStandardList.get(0);
		}
		return null;
	}
	
	@Override
	public WebPage<ContractPayStandard> getAll(Map<String,String> map,Boolean hasPage){
		StringBuilder sb = new StringBuilder();
		Integer page = (map.get("page")==null)? 1: Integer.valueOf(map.get("page"));
		Integer rows = (map.get("rows")==null)? 10: Integer.valueOf(map.get("rows"));
		String sort = (map.get("sort")==null)? "end_date": map.get("sort");
		String order = (map.get("order")==null)? "desc": map.get("order");
		Long total = 0L;
		for(String key:map.keySet()) {
			if(!map.get(key).isEmpty()) {
				if(!(key.equals("page")||key.equals("rows")||key.equals("sort")||key.equals("order"))) {
					sb.append(" and "+StringUtil.propertyToField(key));
					if(key.equals("startDate")) {
						sb.append(" >= '"+map.get(key)+"'");
					}else if(key.equals("endDate")) {
						sb.append(" <= '"+map.get(key)+"'");
					}else if(key.equals("id")){
						sb.append(" = "+map.get(key));
					}
				}
			}
		}
		sb.append(" order by " + StringUtil.propertyToField(sort) + " " + order + ",id asc");
		if(hasPage) {
			total = namedParameterJdbcTemplate.getJdbcOperations().queryForObject(PAY_ALL_COUNT_SELECT+sb.toString(), Long.class);
			sb.append(" limit "+(page-1)*rows+" , " + rows);
		}
		WebPage<ContractPayStandard> webPage=new WebPage<>(total, page);
		RowMapper<ContractPayStandard> rowMapper = new BeanPropertyRowMapper<>(ContractPayStandard.class);
		List<ContractPayStandard> list = namedParameterJdbcTemplate.query(PAY_ALL_SELECT+sb.toString(), rowMapper);
		webPage.setRows(list);
		return webPage;
	}
	@Override
	public Integer save(ContractPayStandard model) {
		SqlParameterSource paramMap = new BeanPropertySqlParameterSource(model);
		KeyHolder keyHolder= new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update(PAY_INSERT, paramMap,keyHolder);
		Integer id = keyHolder.getKey().intValue();//获得主键值
		return id;
	}

	@Override
	public Integer update(ContractPayStandard model) {
		SqlParameterSource paramMap = new BeanPropertySqlParameterSource(model);
		return namedParameterJdbcTemplate.update(PAY_UPDATE, paramMap);
	}
	@Override
	public Boolean getExist(Map<String,String> map) {
		StringBuilder sb = new StringBuilder();
		if(map.containsKey("startDate")&&!map.get("startDate").isEmpty()&&map.containsKey("endDate")&&!map.get("endDate").isEmpty()) {
			sb.append(" and ((start_date BETWEEN '"+map.get("startDate")+"' AND '"+map.get("endDate")+"')");
			sb.append(" or (end_date BETWEEN '"+map.get("startDate")+"' AND '"+map.get("endDate")+"')");
			sb.append(" or ('"+map.get("startDate")+"' BETWEEN start_date AND end_date))");
		}
		if(map.containsKey("id")&&!map.get("id").isEmpty()){
			sb.append(" and id <>"+map.get("id"));
		}
		RowMapper<ContractPayStandard> rowMapper = new BeanPropertyRowMapper<>(ContractPayStandard.class);
		List<ContractPayStandard> list = namedParameterJdbcTemplate.query(PAY_ALL_SELECT+sb.toString(), rowMapper);
		if(list.isEmpty()) {
			return false;
		}else {
			return true;
		}
	}
}
