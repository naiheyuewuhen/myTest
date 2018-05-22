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

import com.manage.dao.UserDao;
import com.manage.model.User;
import com.manage.model.WebPage;
import com.manage.util.StringUtil;

@Repository("userDao")
public class UserDaoImpl implements UserDao{
	private static final String USER_BY_ID_SELECT="select u.*,r.id as role_id,r.name as role_name from user u left join role r on u.role_id=r.id where u.id = :id ";
	private static final String USER_ALL_SELECT="select u.*,r.id as role_id,r.name as role_name from user u left join role r on u.role_id=r.id where 1=1 ";
	private static final String USER_ALL_COUNT_SELECT="select COUNT(1) count from user u where 1=1 ";
	private static final String USER_INSERT="insert into user(username,password,name,sex,phone,role_id,create_user,create_time,update_user,update_time,remark) values (:username,:password,:name,:sex,:phone,:roleId,:createUser,:createTime,:updateUser,:updateTime,:remark)";
	private static final String USER_UPDATE="update user set username=:username,password=:password,name=:name,sex=:sex,phone=:phone,role_id=:roleId,update_user=:updateUser,update_time=:updateTime,remark=:remark where id=:id";
	
	@Resource
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Override
	public User getById(Integer id) {
		Map<String,Integer> paramMap=new HashMap<String,Integer>();
		paramMap.put("id", id);
		RowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);
		List<User> userList = namedParameterJdbcTemplate.query(USER_BY_ID_SELECT, paramMap, rowMapper);
		if(userList.size()>0) {
			return userList.get(0);
		}
		return null;
	}
	
	@Override
	public WebPage<User> getAll(Map<String,String> map,Boolean hasPage){
		StringBuilder sb = new StringBuilder();
		Integer page = (map.get("page")==null)? 1: Integer.valueOf(map.get("page"));
		Integer rows = (map.get("rows")==null)? 10: Integer.valueOf(map.get("rows"));
		String sort = (map.get("sort")==null)? "id": map.get("sort");
		String order = (map.get("order")==null)? "desc": map.get("order");
		Long total = 0L;
		for(String key:map.keySet()) {
			if(!map.get(key).isEmpty()) {
				if(!(key.equals("page")||key.equals("rows")||key.equals("sort")||key.equals("order"))) {
					sb.append(" and u."+StringUtil.propertyToField(key));
					if(key.equals("name")||key.equals("phone")) {
						sb.append(" like '%"+map.get(key)+"%'");
					}else {
						if(key.equals("username")||key.equals("password")) {
							sb.append(" = '"+map.get(key)+"'");
						}else {
							sb.append(" = "+map.get(key));
						}
					}
				}
			}
		}
		sb.append(" order by u." + StringUtil.propertyToField(sort) + " " + order);
		if(hasPage) {
			total = namedParameterJdbcTemplate.getJdbcOperations().queryForObject(USER_ALL_COUNT_SELECT+sb.toString(), Long.class);
			sb.append(" limit "+(page-1)*rows+" , " + rows);
		}
		WebPage<User> webPage=new WebPage<>(total, page);
		RowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);
		List<User> userList = namedParameterJdbcTemplate.query(USER_ALL_SELECT+sb.toString(), rowMapper);
		webPage.setRows(userList);
		return webPage;
	}
	@Override
	public Integer save(User user) {
		SqlParameterSource paramMap = new BeanPropertySqlParameterSource(user);
		KeyHolder keyHolder= new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update(USER_INSERT, paramMap,keyHolder);
		Integer id = keyHolder.getKey().intValue();//获得主键值
		return id;
	}

	@Override
	public Integer update(User user) {
		SqlParameterSource paramMap = new BeanPropertySqlParameterSource(user);
		return namedParameterJdbcTemplate.update(USER_UPDATE, paramMap);
	}
	
	@Override
	public Boolean getExist(Map<String,String> map) {
		StringBuilder sb = new StringBuilder();
		for(String key:map.keySet()) {
			if(!map.get(key).isEmpty()) {
				sb.append(" and u."+StringUtil.propertyToField(key));
				if(key.equals("id")) {
					sb.append(" <> "+map.get(key));
				}else if(key.equals("status")) {
					sb.append(" = "+map.get(key));
				}else {
					sb.append(" = '"+map.get(key)+"'");
				}
			}
		}
		RowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);
		List<User> userList = namedParameterJdbcTemplate.query(USER_ALL_SELECT+sb.toString(), rowMapper);
		if(userList.isEmpty()) {
			return false;
		}else {
			return true;
		}
	}
}
