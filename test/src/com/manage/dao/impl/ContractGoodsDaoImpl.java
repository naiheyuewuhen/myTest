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

import com.manage.dao.ContractGoodsDao;
import com.manage.model.ContractGoods;
import com.manage.model.WebPage;
import com.manage.util.StringUtil;

@Repository("contractGoodsDao")
public class ContractGoodsDaoImpl implements ContractGoodsDao {

	private static final String CONTRACT_GOODS_BY_ID_SELECT="select * from contract_goods where id = :id ";
	private static final String CONTRACT_GOODS_ALL_SELECT="select g.*,i.code as goodsCode,i.name as goodsName,i.standard as goodsStandard from contract_goods g left join goods_info i on g.goods_id=i.id where g.status!=0 ";
	private static final String CONTRACT_GOODS_ALL_COUNT_SELECT="select COUNT(1) count from contract_goods g left join goods_info i on g.goods_id=i.id where status!=0 ";
	private static final String CONTRACT_GOODS_INSERT="insert into contract_goods(contract_id,resolve_id,goods_id,goods_num,price,supplier_ids_advise,supplier_names_advise,supplier_id_reality,supplier_name_reality,create_user,create_time,update_user,update_time,remark) values (:contractId,:resolveId,:goodsId,:goodsNum,:price,:supplierIdsAdvise,:supplierNamesAdvise,:supplierIdReality,:supplierNameReality,:createUser,:createTime,:updateUser,:updateTime,:remark)";
	private static final String CONTRACT_GOODS_RESOLVE_UPDATE="update contract_goods set goods_num=:goodsNum,supplier_ids_advise=:supplierIdsAdvise,supplier_names_advise=:supplierNamesAdvise,update_user=:updateUser,update_time=:updateTime,remark=:remark,status=:status where id=:id";
	private static final String CONTRACT_GOODS_REALITY_UPDATE="update contract_goods set price=:price,supplier_id_reality=:supplierIdReality,supplier_name_reality=:supplierNameReality,reality_user=:realityUser,reality_time=:realityTime where id=:id";
	private static final String CONTRACT_GOODS_UPDATE_UNUSE="update contract_goods set status=0 where resolve_id=:resolveId";
//	private static final String CONTRACT_GOODS_UPDATE="update contract_goods set status=:status where id=:id";
	@Resource
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Override
	public ContractGoods getById(Integer id) {
		Map<String,Integer> paramMap=new HashMap<String,Integer>();
		paramMap.put("id", id);
		RowMapper<ContractGoods> rowMapper = new BeanPropertyRowMapper<>(ContractGoods.class);
		List<ContractGoods> contractGoodsList = namedParameterJdbcTemplate.query(CONTRACT_GOODS_BY_ID_SELECT, paramMap, rowMapper);
		if(contractGoodsList.size()>0) {
			return contractGoodsList.get(0);
		}
		return null;
	}
	@Override
	public WebPage<ContractGoods> getAll(Map<String,Object> map,Boolean hasPage){
		StringBuilder sb = new StringBuilder();
		Integer page = (map.get("page")==null)? 1: Integer.valueOf(map.get("page").toString());
		Integer rows = (map.get("rows")==null)? 10: Integer.valueOf(map.get("rows").toString());
		String sort = (map.get("sort")==null)? "id": map.get("sort").toString();
		String order = (map.get("order")==null)? "desc": map.get("order").toString();
		Long total = 0L;
		for(String key:map.keySet()) {
			if(!StringUtil.isBlank(map.get(key))) {
				if(!(key.equals("page")||key.equals("rows")||key.equals("sort")||key.equals("order"))) {
					if(map.get(key) instanceof Integer) {
						sb.append(" and g."+StringUtil.propertyToField(key));
						sb.append(" = "+map.get(key));
					}else {
						if(key.indexOf("TimeStart")>0) {
							sb.append(" and g."+StringUtil.propertyToField(key.substring(0,key.indexOf("TimeStart"))+"Time"));
							sb.append(" >='"+map.get(key)+"'");
						}else if(key.indexOf("TimeEnd")>0) {
							sb.append(" and g."+StringUtil.propertyToField(key.substring(0,key.indexOf("TimeEnd"))+"Time"));
							sb.append(" <='"+map.get(key)+"'");
						}else {
							sb.append(" like '%"+map.get(key)+"%'");
						}
					}
				}
			}
		}
		sb.append(" order by g." + StringUtil.propertyToField(sort) + " " + order);
		if(hasPage) {
			total = namedParameterJdbcTemplate.getJdbcOperations().queryForObject(CONTRACT_GOODS_ALL_COUNT_SELECT+sb.toString(), Long.class);
			sb.append(" limit "+(page-1)*rows+" , " + rows);
		}
		WebPage<ContractGoods> webPage=new WebPage<>(total, page);
		RowMapper<ContractGoods> rowMapper = new BeanPropertyRowMapper<>(ContractGoods.class);
		List<ContractGoods> contractGoodsList = namedParameterJdbcTemplate.query(CONTRACT_GOODS_ALL_SELECT+sb.toString(), rowMapper);
		webPage.setRows(contractGoodsList);
		return webPage;
	}
	@Override
	public Integer save(ContractGoods contractGoods) {
		SqlParameterSource paramMap = new BeanPropertySqlParameterSource(contractGoods);
		KeyHolder keyHolder= new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update(CONTRACT_GOODS_INSERT, paramMap,keyHolder);
		Integer id = keyHolder.getKey().intValue();//获得主键值
		return id;
	}
	@Override
	public Integer updateUnuse(Integer resolveId) {
		Map<String,Integer> map=new HashMap<String,Integer>();
		map.put("resolveId", resolveId);
		return namedParameterJdbcTemplate.update(CONTRACT_GOODS_UPDATE_UNUSE, map);
	}
//	@Override
//	public Integer updateForResolve(ContractGoods contractGoods) {
//		SqlParameterSource paramMap = new BeanPropertySqlParameterSource(contractGoods);
//		return namedParameterJdbcTemplate.update(CONTRACT_GOODS_RESOLVE_UPDATE, paramMap);
//	}
//	@Override
//	public Integer updateForReality(ContractGoods contractGoods) {
//		SqlParameterSource paramMap = new BeanPropertySqlParameterSource(contractGoods);
//		return namedParameterJdbcTemplate.update(CONTRACT_GOODS_REALITY_UPDATE, paramMap);
//	}
	@Override
	public Boolean updateForResolveBatch(List<ContractGoods> contractGoodsList) {
		if(contractGoodsList.size()>0) {
			namedParameterJdbcTemplate.batchUpdate(CONTRACT_GOODS_RESOLVE_UPDATE, SqlParameterSourceUtils.createBatch(contractGoodsList.toArray()));
		}
		return true;
	}
	@Override
	public Boolean updateForRealityBatch(List<ContractGoods> contractGoodsList) {
		if(contractGoodsList.size()>0) {
			namedParameterJdbcTemplate.batchUpdate(CONTRACT_GOODS_REALITY_UPDATE, SqlParameterSourceUtils.createBatch(contractGoodsList.toArray()));
		}
		return true;
	}
	@Override
	public Boolean saveBatch(List<ContractGoods> contractGoodsList) {
		if(contractGoodsList.size()>0) {
			namedParameterJdbcTemplate.batchUpdate(CONTRACT_GOODS_INSERT, SqlParameterSourceUtils.createBatch(contractGoodsList.toArray()));
		}
		return true;
	}
	@Override
	public Boolean getExist(Map<String,Object> map) {
		/*StringBuilder sb = new StringBuilder();
		for(String key:map.keySet()) {
			if(!StringUtil.isBlank(map.get(key))) {
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
		RowMapper<ContractGoods> rowMapper = new BeanPropertyRowMapper<>(ContractGoods.class);
		List<ContractGoods> contractGoodsList = namedParameterJdbcTemplate.query(CONTRACT_GOODS_ALL_SELECT+sb.toString(), rowMapper);
		if(contractGoodsList.isEmpty()) {
			return false;
		}else {
			return true;
		}*/
		return false;
	}
}
