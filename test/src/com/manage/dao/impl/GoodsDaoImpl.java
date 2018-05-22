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

import com.manage.dao.GoodsDao;
import com.manage.model.GoodsInfo;
import com.manage.model.WebPage;
import com.manage.util.StringUtil;

@Repository("goodsDao")
public class GoodsDaoImpl implements GoodsDao{
	private static final String GOODS_BY_ID_SELECT="select * from goods_info where id = :id ";
	private static final String GOODS_ALL_SELECT="select * from goods_info where 1=1 ";
	private static final String GOODS_ALL_COUNT_SELECT="select COUNT(1) count from goods_info where 1=1 ";
	private static final String GOODS_INSERT="insert into goods_info(code,name,standard,create_time,remark,sort_num) values (:code,:name,:standard,:createTime,:remark,:sortNum)";
	private static final String GOODS_UPDATE="update goods_info set code=:code,name=:name,standard=:standard,remark=:remark,sort_num=:sortNum where id=:id";
	
	@Resource
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Override
	public GoodsInfo getById(Integer id) {
		Map<String,Integer> paramMap=new HashMap<String,Integer>();
		paramMap.put("id", id);
		RowMapper<GoodsInfo> rowMapper = new BeanPropertyRowMapper<>(GoodsInfo.class);
		List<GoodsInfo> goodsInfoList = namedParameterJdbcTemplate.query(GOODS_BY_ID_SELECT, paramMap, rowMapper);
		if(goodsInfoList.size()>0) {
			return goodsInfoList.get(0);
		}
		return null;
	}
	
	@Override
	public WebPage<GoodsInfo> getAll(Map<String,String> map,Boolean hasPage){
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
			total = namedParameterJdbcTemplate.getJdbcOperations().queryForObject(GOODS_ALL_COUNT_SELECT+sb.toString(), Long.class);
			sb.append(" limit "+(page-1)*rows+" , " + rows);
		}
		WebPage<GoodsInfo> webPage=new WebPage<>(total, page);
		RowMapper<GoodsInfo> rowMapper = new BeanPropertyRowMapper<>(GoodsInfo.class);
		List<GoodsInfo> goodsInfoList = namedParameterJdbcTemplate.query(GOODS_ALL_SELECT+sb.toString(), rowMapper);
		webPage.setRows(goodsInfoList);
		return webPage;
	}
	@Override
	public Integer save(GoodsInfo goodsInfo) {
		SqlParameterSource paramMap = new BeanPropertySqlParameterSource(goodsInfo);
		KeyHolder keyHolder= new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update(GOODS_INSERT, paramMap,keyHolder);
		Integer id = keyHolder.getKey().intValue();//获得主键值
		return id;
	}

	@Override
	public Integer update(GoodsInfo goodsInfo) {
		SqlParameterSource paramMap = new BeanPropertySqlParameterSource(goodsInfo);
		return namedParameterJdbcTemplate.update(GOODS_UPDATE, paramMap);
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
		RowMapper<GoodsInfo> rowMapper = new BeanPropertyRowMapper<>(GoodsInfo.class);
		List<GoodsInfo> goodsInfoList = namedParameterJdbcTemplate.query(GOODS_ALL_SELECT+sb.toString(), rowMapper);
		if(goodsInfoList.isEmpty()) {
			return false;
		}else {
			return true;
		}
	}
}
