package com.manage.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.manage.dao.ModuleDao;
import com.manage.model.ModuleInfo;

@Repository("moduleDao")
public class ModuleDaoImpl implements ModuleDao{
	
	private static final String MODULE_BY_LEVEL_SELECT="select m.* from module m LEFT JOIN authority a ON m.id=a.module_id where m.type='url' and m.level<>5 and m.status=1 and (m.father_id=:fatherId or m.father_id in (SELECT id FROM module where father_id=:fatherId))";
	private static final String MODULE_TREE_SELECT="select *,name as text from module where status=1 ";
	private static final String MODULE_BUTTON_SELECT="select m.* from module m LEFT JOIN authority a ON m.id=a.module_id where m.type='button' and m.level=5 and m.status=1";
	
	@Resource
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Override
	public List<ModuleInfo> getByFatherId(Integer fatherId, Integer roleId) {
		StringBuilder sb=new StringBuilder();
		if(roleId!=null) {
			sb.append(" and a.role_id=");
			sb.append(roleId);
		}
		sb.append(" GROUP BY m.id ORDER BY m.sort_num DESC,m.id ASC");
		Map<String,Integer> paramMap=new HashMap<String,Integer>();
		paramMap.put("fatherId", fatherId);
		RowMapper<ModuleInfo> rowMapper = new BeanPropertyRowMapper<>(ModuleInfo.class);
		List<ModuleInfo> moduleInfoList = namedParameterJdbcTemplate.query(MODULE_BY_LEVEL_SELECT+sb.toString(), paramMap, rowMapper);
		return moduleInfoList;
	}
	@Override
	public List<ModuleInfo> getButtonByRoleId(Integer roleId) {
		StringBuilder sb=new StringBuilder();
		if(roleId!=null) {
			sb.append(" and a.role_id=");
			sb.append(roleId);
		}
		sb.append(" GROUP BY m.id ORDER BY m.sort_num DESC,m.id ASC");
		RowMapper<ModuleInfo> rowMapper = new BeanPropertyRowMapper<>(ModuleInfo.class);
		List<ModuleInfo> moduleInfoList = namedParameterJdbcTemplate.query(MODULE_BUTTON_SELECT+sb.toString(), rowMapper);
		return moduleInfoList;
	}
	@Override
	public List<ModuleInfo> getTreeAll() {
		StringBuilder sb=new StringBuilder();
		sb.append(" ORDER BY sort_num DESC,id ASC");
		RowMapper<ModuleInfo> rowMapper = new BeanPropertyRowMapper<>(ModuleInfo.class);
		List<ModuleInfo> moduleInfoList = namedParameterJdbcTemplate.query(MODULE_TREE_SELECT+sb.toString(), rowMapper);
		Map<Integer,ModuleInfo> moduleMap=new HashMap<Integer,ModuleInfo>();
		List<ModuleInfo> list = new ArrayList<ModuleInfo>();
		for(ModuleInfo moduleInfo : moduleInfoList) {
			moduleMap.put(moduleInfo.getId(), moduleInfo);
		}
		for (ModuleInfo moduleInfo1 : moduleInfoList)
		{
			if (0 == moduleInfo1.getFatherId())
			{
				list.add(moduleInfo1);
			}
			if (moduleMap.get(moduleInfo1.getFatherId()) != null)
			{
				moduleMap.get(moduleInfo1.getFatherId()).getChildren().add(moduleMap.get(moduleInfo1.getId()));
			}
		}
		return list;
	}
}
