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

import com.manage.dao.RoleDao;
import com.manage.model.Role;
import com.manage.model.WebPage;
import com.manage.util.StringUtil;

@Repository("roleDao")
public class RoleDaoImpl implements RoleDao{
	private static final String ROLE_BY_ID_SELECT="select * from role where id = :id ";
	private static final String ROLE_ALL_SELECT="select * from role where status=1 ";
	private static final String ROLE_ALL_COUNT_SELECT="select COUNT(1) count from role where status=1 ";
	private static final String ROLE_INSERT="insert into role(name,remark,create_user,create_time,update_user,update_time) values (:name,:remark,:createUser,:createTime,:updateUser,:updateTime)";
	private static final String ROLE_UPDATE="update role set name=:name,remark=:remark,update_user=:updateUser,update_time=:updateTime where id=:id";
	
	@Resource
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Override
	public Role getById(Integer id) {
		Map<String,Integer> paramMap=new HashMap<String,Integer>();
		paramMap.put("id", id);
		RowMapper<Role> rowMapper = new BeanPropertyRowMapper<>(Role.class);
		List<Role> roleList = namedParameterJdbcTemplate.query(ROLE_BY_ID_SELECT, paramMap, rowMapper);
		if(roleList.size()>0) {
			return roleList.get(0);
		}
		return null;
	}
	
	@Override
	public WebPage<Role> getAll(Map<String,String> map,Boolean hasPage){
		StringBuilder sb = new StringBuilder();
		Integer page = (map.get("page")==null)? 1: Integer.valueOf(map.get("page"));
		Integer rows = (map.get("rows")==null)? 10: Integer.valueOf(map.get("rows"));
		String sort = (map.get("sort")==null)? "id": map.get("sort");
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
			total = namedParameterJdbcTemplate.getJdbcOperations().queryForObject(ROLE_ALL_COUNT_SELECT+sb.toString(), Long.class);
			sb.append(" limit "+(page-1)*rows+" , " + rows);
		}
		WebPage<Role> webPage=new WebPage<>(total, page);
		RowMapper<Role> rowMapper = new BeanPropertyRowMapper<>(Role.class);
		List<Role> roleList = namedParameterJdbcTemplate.query(ROLE_ALL_SELECT+sb.toString(), rowMapper);
		webPage.setRows(roleList);
		return webPage;
	}
	@Override
	public Integer save(Role role) {
		SqlParameterSource paramMap = new BeanPropertySqlParameterSource(role);
		KeyHolder keyHolder= new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update(ROLE_INSERT, paramMap,keyHolder);
		Integer id = keyHolder.getKey().intValue();//获得主键值
		return id;
	}

	@Override
	public Integer update(Role role) {
		SqlParameterSource paramMap = new BeanPropertySqlParameterSource(role);
		return namedParameterJdbcTemplate.update(ROLE_UPDATE, paramMap);
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
		RowMapper<Role> rowMapper = new BeanPropertyRowMapper<>(Role.class);
		List<Role> goodsInfoList = namedParameterJdbcTemplate.query(ROLE_ALL_SELECT+sb.toString(), rowMapper);
		if(goodsInfoList.isEmpty()) {
			return false;
		}else {
			return true;
		}
	}
}
