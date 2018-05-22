package com.manage.dao.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.manage.dao.UserRoleDao;
import com.manage.model.UserRole;

@Deprecated
@Repository("userRoleDao")
public class UserRoleDaoImpl implements UserRoleDao{
	private static final String INSERT="insert into user_role(user_id,role_id) values (:userId,:roleId)";
	private static final String DELETE="DELETE from user_role where user_id=:userId";
	
	@Resource
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Override
	public Integer save(UserRole userRole) {
		SqlParameterSource paramMap = new BeanPropertySqlParameterSource(userRole);
		KeyHolder keyHolder= new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update(INSERT, paramMap,keyHolder);
		Integer id = keyHolder.getKey().intValue();//获得主键值
		return id;
	}

	@Override
	public Integer delete(Integer userId) {
		Map<String,Object> paramMap = new HashMap<>();  
        paramMap.put("userId", userId);  
        return namedParameterJdbcTemplate.update(DELETE, paramMap); 
	}
}
