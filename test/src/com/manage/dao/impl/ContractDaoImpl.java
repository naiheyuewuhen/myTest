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

import com.manage.dao.ContractDao;
import com.manage.model.ContractInfo;
import com.manage.model.WebPage;
import com.manage.util.StringUtil;

@Repository("contractDao")
public class ContractDaoImpl implements ContractDao{
	
	private static final String CONTRACT_BY_ID_SELECT="select * from contract_info where id = :id ";
	private static final String CONTRACT_ALL_SELECT="select * from contract_info where 1=1 ";
	private static final String CONTRACT_ALL_COUNT_SELECT="select COUNT(1) count from contract_info where 1=1 ";
	private static final String CONTRACT_INSERT="insert into contract_info(father_id,code,first_party,first_address,first_linkman,first_phone,second_party,second_address,second_linkman,second_phone,contract_date,create_user,create_time,update_user,update_time,remark,total_amount,contract_type) values (:fatherId,:code,:firstParty,:firstAddress,:firstLinkman,:firstPhone,:secondParty,:secondAddress,:secondLinkman,:secondPhone,:contractDate,:createUser,:createTime,:updateUser,:updateTime,:remark,:totalAmount,:contractType)";
	private static final String CONTRACT_UPDATE="update contract_info set father_id=:fatherId,code=:code,first_party=:firstParty,first_address=:firstAddress,first_linkman=:firstLinkman,first_phone=:firstPhone,second_party=:secondParty,second_address=:secondAddress,second_linkman=:secondLinkman,second_phone=:secondPhone,contract_date=:contractDate,update_user=:updateUser,update_time=:updateTime,remark=:remark,total_amount=:totalAmount,contract_type=:contractType where id=:id";
	private static final String CONTRACT_AUDITING_UPDATE="update contract_info set status=:status,auditor=:auditor,auditing_time=:auditingTime,auditing_remark=:auditingRemark where id=:id";
	private static final String CONTRACT_STATUS_UPDATE="update contract_info set status=:status where id=:id";
	
	@Resource
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Override
	public ContractInfo getById(Integer id) {
		Map<String,Integer> paramMap=new HashMap<String,Integer>();
		paramMap.put("id", id);
		RowMapper<ContractInfo> rowMapper = new BeanPropertyRowMapper<>(ContractInfo.class);
		List<ContractInfo> contractInfoList = namedParameterJdbcTemplate.query(CONTRACT_BY_ID_SELECT, paramMap, rowMapper);
		if(contractInfoList.size()>0) {
			return contractInfoList.get(0);
		}
		return null;
	}
	
	@Override
	public WebPage<ContractInfo> getAll(Map<String,String> map,Boolean hasPage){
		StringBuilder sb = new StringBuilder();
		Integer page = (map.get("page")==null)? 1: Integer.valueOf(map.get("page"));
		Integer rows = (map.get("rows")==null)? 10: Integer.valueOf(map.get("rows"));
		String sort = (map.get("sort")==null)? "id": map.get("sort");
		String order = (map.get("order")==null)? "desc": map.get("order");
		Long total = 0L;
		for(String key:map.keySet()) {
			if(!map.get(key).isEmpty()) {
				if(!(key.equals("page")||key.equals("rows")||key.equals("sort")||key.equals("order"))) {
					if(key.equals("statusArr")) {
						sb.append(" and status in (");
						sb.append(map.get(key));
						sb.append(")");
					}else {
						sb.append(" and "+StringUtil.propertyToField(key));
					}
					if(key.equals("id")||key.equals("fatherId")||key.equals("status")) {
						sb.append(" = "+map.get(key));
					}else if(!key.equals("statusArr")){
						sb.append(" like '%"+map.get(key)+"%'");
					}
				}
			}
		}
		sb.append(" order by " + StringUtil.propertyToField(sort) + " " + order);
		if(hasPage) {
			total = namedParameterJdbcTemplate.getJdbcOperations().queryForObject(CONTRACT_ALL_COUNT_SELECT+sb.toString(), Long.class);
			sb.append(" limit "+(page-1)*rows+" , " + rows);
		}
		WebPage<ContractInfo> webPage=new WebPage<>(total, page);
		RowMapper<ContractInfo> rowMapper = new BeanPropertyRowMapper<>(ContractInfo.class);
		List<ContractInfo> contractInfoList = namedParameterJdbcTemplate.query(CONTRACT_ALL_SELECT+sb.toString(), rowMapper);
		webPage.setRows(contractInfoList);
		return webPage;
	}
	@Override
	public Integer save(ContractInfo contractInfo) {
		SqlParameterSource paramMap = new BeanPropertySqlParameterSource(contractInfo);
		KeyHolder keyHolder= new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update(CONTRACT_INSERT, paramMap,keyHolder);
		Integer id = keyHolder.getKey().intValue();//获得主键值
		return id;
	}

	@Override
	public Integer update(ContractInfo contractInfo) {
		SqlParameterSource paramMap = new BeanPropertySqlParameterSource(contractInfo);
		return namedParameterJdbcTemplate.update(CONTRACT_UPDATE, paramMap);
	}
	@Override
	public Integer updateStatus(Integer contractInfoId,Integer status) {
		Map<String,Integer> map=new HashMap<String,Integer>();
		map.put("id", contractInfoId);
		map.put("status", status);
		return namedParameterJdbcTemplate.update(CONTRACT_STATUS_UPDATE, map);
	}
	@Override
	public Integer updateAuditing(ContractInfo contractInfo) {
		SqlParameterSource paramMap = new BeanPropertySqlParameterSource(contractInfo);
		return namedParameterJdbcTemplate.update(CONTRACT_AUDITING_UPDATE, paramMap);
	}
	@Override
	public Boolean getExist(Map<String,String> map) {
		StringBuilder sb = new StringBuilder();
		for(String key:map.keySet()) {
			if(!map.get(key).isEmpty()) {
				sb.append(" and "+StringUtil.propertyToField(key));
				if(key.equals("id")) {
					sb.append(" <> "+map.get(key));
				}else if(key.equals("fatherId")||key.equals("status")) {
					sb.append(" = "+map.get(key));
				}else {
					sb.append(" = '"+map.get(key)+"'");
				}
			}
		}
		RowMapper<ContractInfo> rowMapper = new BeanPropertyRowMapper<>(ContractInfo.class);
		List<ContractInfo> contractInfoList = namedParameterJdbcTemplate.query(CONTRACT_ALL_SELECT+sb.toString(), rowMapper);
		if(contractInfoList.isEmpty()) {
			return false;
		}else {
			return true;
		}
	}
}
