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

import com.manage.dao.GoodsGroupDao;
import com.manage.model.GoodsGroup;
import com.manage.model.WebPage;
import com.manage.util.StringUtil;

@Repository("goodsGroupDao")
public class GoodsGroupDaoImpl implements GoodsGroupDao {
	
	private static final String GOODSGROUP_BY_ID_SELECT="select * from goods_group where id = :id ";
	private static final String GOODSGROUP_ALL_SELECT="select * from goods_group where 1=1 ";
	private static final String GOODSGROUP_ALL_COUNT_SELECT="select COUNT(1) count from goods_group where 1=1 ";
	private static final String GOODSGROUP_INSERT="insert into goods_group(code,name,remark,status) values (:code,:name,:remark,:status)";
	private static final String GOODSGROUP_UPDATE="update goods_group set code=:code,name=:name,remark=:remark,status=:status where id=:id";
	private static final String GOODSGROUP_UPDATE_STATUS="update goods_group set status=:status where id=:id";
	
	@Resource
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Override
	public Integer save(GoodsGroup goodsGroup) {
		SqlParameterSource paramMap = new BeanPropertySqlParameterSource(goodsGroup);
		KeyHolder keyHolder= new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update(GOODSGROUP_INSERT, paramMap,keyHolder);
		Integer id = keyHolder.getKey().intValue();//获得主键值
		return id;
	}

	@Override
	public WebPage<GoodsGroup> getAll(Map<String, String> map, Boolean hasPage) {
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
		sb.append(" order by " + StringUtil.propertyToField(sort) + " " + order);
		if(hasPage) {
			total = namedParameterJdbcTemplate.getJdbcOperations().queryForObject(GOODSGROUP_ALL_COUNT_SELECT+sb.toString(), Long.class);
			sb.append(" limit "+(page-1)*rows+" , " + rows);
		}
		WebPage<GoodsGroup> webPage=new WebPage<>(total, page);
		RowMapper<GoodsGroup> rowMapper = new BeanPropertyRowMapper<>(GoodsGroup.class);
		List<GoodsGroup> list = namedParameterJdbcTemplate.query(GOODSGROUP_ALL_SELECT+sb.toString(), rowMapper);
		webPage.setRows(list);
		return webPage;
	}

	@Override
	public Integer update(GoodsGroup goodsGroup) {
		SqlParameterSource paramMap = new BeanPropertySqlParameterSource(goodsGroup);
		return namedParameterJdbcTemplate.update(GOODSGROUP_UPDATE, paramMap);
	}
	@Override
	public Integer updateStatus(GoodsGroup goodsGroup) {
		SqlParameterSource paramMap = new BeanPropertySqlParameterSource(goodsGroup);
		return namedParameterJdbcTemplate.update(GOODSGROUP_UPDATE_STATUS, paramMap);
	}

	@Override
	public GoodsGroup getById(Integer id) {
		Map<String,Integer> paramMap=new HashMap<String,Integer>();
		paramMap.put("id", id);
		RowMapper<GoodsGroup> rowMapper = new BeanPropertyRowMapper<>(GoodsGroup.class);
		List<GoodsGroup> list = namedParameterJdbcTemplate.query(GOODSGROUP_BY_ID_SELECT, paramMap, rowMapper);
		if(list.size()>0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public Boolean getExist(Map<String, String> map) {
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
		RowMapper<GoodsGroup> rowMapper = new BeanPropertyRowMapper<>(GoodsGroup.class);
		List<GoodsGroup> list = namedParameterJdbcTemplate.query(GOODSGROUP_ALL_SELECT+sb.toString(), rowMapper);
		if(list.isEmpty()) {
			return false;
		}else {
			return true;
		}
	}

}
