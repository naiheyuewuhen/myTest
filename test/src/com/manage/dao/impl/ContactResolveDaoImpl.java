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
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.manage.dao.ContactResolveDao;
import com.manage.model.ContactResolve;
import com.manage.model.WebPage;
import com.manage.util.StringUtil;

@Repository("contactResolveDao")
public class ContactResolveDaoImpl implements ContactResolveDao {
	
	private static final String CONTRACT_RESOLVE_BY_ID_SELECT="select * from contact_resolve where id = :id ";
	private static final String CONTRACT_RESOLVE_ALL_SELECT="select r.*,i.code as contractCode from contact_resolve r left join contract_info i on r.contract_id=i.id where r.status!=0 ";
	private static final String CONTRACT_RESOLVE_ALL_COUNT_SELECT="select COUNT(1) count from contact_resolve r left join contract_info i on r.contract_id=i.id where r.status!=0 ";
	private static final String CONTRACT_RESOLVE_INSERT="insert into contact_resolve(contract_id,resolve_name,create_user,create_time,update_user,update_time,remark,status) values (:contractId,:resolveName,:createUser,:createTime,:updateUser,:updateTime,:remark,:status)";
	private static final String CONTRACT_RESOLVE_UPDATE="update contact_resolve set resolve_name=:resolveName,update_user=:updateUser,update_time=:updateTime,remark=:remark,status=:status where id=:id";
	private static final String CONTRACT_RESOLVE_UPDATE_STATUS="update contact_resolve set update_user=:updateUser,update_time=:updateTime,status=:status where id=:id";
	private static final String CONTRACT_RESOLVE_UPDATE_UNUSE="update contact_resolve set status=0 where contract_id=:contractId";
	private static final String CONTRACT_RESOLVE_UPDATE_BY_IDS_STATUS="update contact_resolve set status=:status where id in (:ids)";

	@Resource
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Override
	public Integer save(ContactResolve contactResolve) {
		SqlParameterSource paramMap = new BeanPropertySqlParameterSource(contactResolve);
		KeyHolder keyHolder= new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update(CONTRACT_RESOLVE_INSERT, paramMap,keyHolder);
		Integer id = keyHolder.getKey().intValue();//获得主键值
		return id;
	}

	@Override
	public WebPage<ContactResolve> getAll(Map<String, String> map, Boolean hasPage) {
		StringBuilder sb = new StringBuilder();
		Integer page = (map.get("page")==null)? 1: Integer.valueOf(map.get("page"));
		Integer rows = (map.get("rows")==null)? 10: Integer.valueOf(map.get("rows"));
		String sort = (map.get("sort")==null)? "id": map.get("sort");
		String order = (map.get("order")==null)? "desc": map.get("order");
		Long total = 0L;
		for(String key:map.keySet()) {
			if(!map.get(key).isEmpty()) {
				if(!(key.equals("page")||key.equals("rows")||key.equals("sort")||key.equals("order"))) {
					if(key.equals("contractCode")) {
						sb.append(" and i.code");
					}else if(key.equals("statusArr")) {
						sb.append(" and r.status in (");
						sb.append(map.get(key));
						sb.append(")");
					}else {
						sb.append(" and r."+StringUtil.propertyToField(key));
					}
					if(key.equals("id")||key.equals("contractId")||key.equals("status")) {
						sb.append(" = "+map.get(key));
					}else if(!key.equals("statusArr")){
						sb.append(" like '%"+map.get(key)+"%'");
					}
				}
			}
		}
		sb.append(" order by r." + StringUtil.propertyToField(sort) + " " + order);
		if(hasPage) {
			total = namedParameterJdbcTemplate.getJdbcOperations().queryForObject(CONTRACT_RESOLVE_ALL_COUNT_SELECT+sb.toString(), Long.class);
			sb.append(" limit "+(page-1)*rows+" , " + rows);
		}
		WebPage<ContactResolve> webPage=new WebPage<>(total, page);
		RowMapper<ContactResolve> rowMapper = new BeanPropertyRowMapper<>(ContactResolve.class);
		List<ContactResolve> contactResolveList = namedParameterJdbcTemplate.query(CONTRACT_RESOLVE_ALL_SELECT+sb.toString(), rowMapper);
		webPage.setRows(contactResolveList);
		return webPage;
	}

	@Override
	public Integer update(ContactResolve contactResolve) {
		SqlParameterSource paramMap = new BeanPropertySqlParameterSource(contactResolve);
		return namedParameterJdbcTemplate.update(CONTRACT_RESOLVE_UPDATE, paramMap);
	}
	@Override
	public Integer updateStatus(ContactResolve contactResolve) {
		SqlParameterSource paramMap = new BeanPropertySqlParameterSource(contactResolve);
		return namedParameterJdbcTemplate.update(CONTRACT_RESOLVE_UPDATE_STATUS, paramMap);
	}
	@Override
	public Integer updateUnuse(Integer contractId) {
		Map<String,Integer> map=new HashMap<String,Integer>();
		map.put("contractId", contractId);
		return namedParameterJdbcTemplate.update(CONTRACT_RESOLVE_UPDATE_UNUSE, map);
	}
	@Override
	public Integer updateStatusByIds(String ids,Integer status) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("ids", ids);
		map.put("status", status);
		return namedParameterJdbcTemplate.update(CONTRACT_RESOLVE_UPDATE_BY_IDS_STATUS, map);
	}
	@Override
	public Boolean saveBatch(List<ContactResolve> contactResolveList) {
		if(contactResolveList.size()>0) {
			namedParameterJdbcTemplate.batchUpdate(CONTRACT_RESOLVE_INSERT, SqlParameterSourceUtils.createBatch(contactResolveList.toArray()));
		}
		return true;
	}
	@Override
	public Boolean updateBatch(List<ContactResolve> contactResolveList) {
		if(contactResolveList.size()>0) {
			namedParameterJdbcTemplate.batchUpdate(CONTRACT_RESOLVE_UPDATE, SqlParameterSourceUtils.createBatch(contactResolveList.toArray()));
		}
		return true;
	}

	@Override
	public ContactResolve getById(Integer id) {
		Map<String,Integer> paramMap=new HashMap<String,Integer>();
		paramMap.put("id", id);
		RowMapper<ContactResolve> rowMapper = new BeanPropertyRowMapper<>(ContactResolve.class);
		List<ContactResolve> contactResolveList = namedParameterJdbcTemplate.query(CONTRACT_RESOLVE_BY_ID_SELECT, paramMap, rowMapper);
		if(contactResolveList.size()>0) {
			return contactResolveList.get(0);
		}
		return null;
	}

	@Override
	public Boolean getExist(Map<String, String> map) {
		StringBuilder sb = new StringBuilder();
		for(String key:map.keySet()) {
			if(!map.get(key).isEmpty()) {
				sb.append(" and r."+StringUtil.propertyToField(key));
				if(key.equals("id")) {
					sb.append(" <> "+map.get(key));
				}else if(key.equals("contractId")||key.equals("status")) {
					sb.append(" = "+map.get(key));
				}else {
					sb.append(" = '"+map.get(key)+"'");
				}
			}
		}
		RowMapper<ContactResolve> rowMapper = new BeanPropertyRowMapper<>(ContactResolve.class);
		List<ContactResolve> contactResolveList = namedParameterJdbcTemplate.query(CONTRACT_RESOLVE_ALL_SELECT+sb.toString(), rowMapper);
		if(contactResolveList.isEmpty()) {
			return false;
		}else {
			return true;
		}
	}

}
