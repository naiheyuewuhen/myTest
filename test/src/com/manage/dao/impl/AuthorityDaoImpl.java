package com.manage.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;

import com.manage.dao.AuthorityDao;
import com.manage.model.Authority;

@Repository("authorityDao")
public class AuthorityDaoImpl implements AuthorityDao{
	private static final String AUTHORITY_INSERT="insert into authority(role_id,module_id) values (:roleId,:moduleId)";
	private static final String AUTHORITY_DELETE="DELETE from authority where role_id=:roleId";
	private static final String AUTHORITY_SELECT="select module_id from authority where role_id=:roleId";
	@Resource
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Override
	public Boolean saveBatch(List<Authority> authorityList) {
		if(authorityList.size()>0) {
			namedParameterJdbcTemplate.batchUpdate(AUTHORITY_INSERT, SqlParameterSourceUtils.createBatch(authorityList.toArray()));
		}
		return true;
	}

	@Override
	public Integer delete(Integer roleId) {
		Map<String,Object> paramMap = new HashMap<>();  
        paramMap.put("roleId", roleId);  
        return namedParameterJdbcTemplate.update(AUTHORITY_DELETE, paramMap); 
	}

	@Override
	public List<Integer> getModulesByRoleId(Integer roleId) {
		Map<String,Integer> paramMap=new HashMap<String,Integer>();
		paramMap.put("roleId", roleId);
		return namedParameterJdbcTemplate.queryForList(AUTHORITY_SELECT, paramMap,Integer.class);
	}
}
