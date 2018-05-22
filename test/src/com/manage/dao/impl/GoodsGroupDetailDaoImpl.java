package com.manage.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;

import com.manage.dao.GoodsGroupDetailDao;
import com.manage.model.GoodsGroupDetail;
import com.manage.model.WebPage;
import com.manage.util.StringUtil;

@Repository("goodsGroupDetailDao")
public class GoodsGroupDetailDaoImpl implements GoodsGroupDetailDao {

	private static final String GOODSGROUPDETAIL_SELECT="select d.*,i.code as goodsCode,i.name as goodsName,i.standard as goodsStandard from goods_group_detail d left join goods_info i on d.goods_id=i.id where 1=1 ";
	private static final String GOODSGROUPDETAIL_INSERT="insert into goods_group_detail(group_id,goods_id,goods_num,status) values (:groupId,:goodsId,:goodsNum,:status)";
	private static final String GOODSGROUPDETAIL_UPDATE_UNUSE="update goods_group_detail set status=0 where group_id=:groupId";
	private static final String GOODSGROUPDETAIL_UPDATE="update goods_group_detail set goods_num=:goodsNum,status=:status where id=:id";
	
	@Resource
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	@Override
	public WebPage<GoodsGroupDetail> getAll(Map<String,String> map,Boolean hasPage){
		StringBuilder sb = new StringBuilder();
		Integer page = (map.get("page")==null)? 1: Integer.valueOf(map.get("page"));
		Integer rows = (map.get("rows")==null)? 10: Integer.valueOf(map.get("rows"));
		String sort = (map.get("sort")==null)? "id": map.get("sort");
		String order = (map.get("order")==null)? "desc": map.get("order");
		Long total = 0L;
		for(String key:map.keySet()) {
			if(!map.get(key).isEmpty()) {
				if(!(key.equals("page")||key.equals("rows")||key.equals("sort")||key.equals("order"))) {
					sb.append(" and d."+StringUtil.propertyToField(key));
					if(key.equals("id")||key.equals("groupId")||key.equals("goodsId")||key.equals("status")) {
						sb.append(" = "+map.get(key));
					}else {
						sb.append(" like '%"+map.get(key)+"%'");
					}
				}
			}
		}
		sb.append(" order by d." + StringUtil.propertyToField(sort) + " " + order);
		if(hasPage) {
			total = namedParameterJdbcTemplate.getJdbcOperations().queryForObject(GOODSGROUPDETAIL_SELECT+sb.toString(), Long.class);
			sb.append(" limit "+(page-1)*rows+" , " + rows);
		}
		WebPage<GoodsGroupDetail> webPage=new WebPage<>(total, page);
		RowMapper<GoodsGroupDetail> rowMapper = new BeanPropertyRowMapper<>(GoodsGroupDetail.class);
		List<GoodsGroupDetail> list = namedParameterJdbcTemplate.query(GOODSGROUPDETAIL_SELECT+sb.toString(), rowMapper);
		webPage.setRows(list);
		return webPage;
	}
	@Override
	public Boolean saveBatch(List<GoodsGroupDetail> goodsGroupDetailList) {
		if(goodsGroupDetailList.size()>0) {
			namedParameterJdbcTemplate.batchUpdate(GOODSGROUPDETAIL_INSERT, SqlParameterSourceUtils.createBatch(goodsGroupDetailList.toArray()));
		}
		return true;
	}
	@Override
	public Integer updateUnuse(Integer groupId) {
		Map<String,Integer> map=new HashMap<String,Integer>();
		map.put("groupId", groupId);
		return namedParameterJdbcTemplate.update(GOODSGROUPDETAIL_UPDATE_UNUSE, map);
	}
	@Override
	public Boolean updateBatch(List<GoodsGroupDetail> goodsGroupDetailList) {
		if(goodsGroupDetailList.size()>0) {
			namedParameterJdbcTemplate.batchUpdate(GOODSGROUPDETAIL_UPDATE, SqlParameterSourceUtils.createBatch(goodsGroupDetailList.toArray()));
		}
		return true;
	}
}
