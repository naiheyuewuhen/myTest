package com.manage.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;

import com.manage.dao.PaymentDao;
import com.manage.model.Payment;
import com.manage.model.WebPage;
import com.manage.util.StringUtil;

@Repository("paymentDao")
public class PaymentDaoImpl implements PaymentDao {
	
	private static final String PAYMENT_AGREED_SELECT="select * from payment_agreed where 1=1 ";
	private static final String PAYMENT_AGREED_INSERT="insert into payment_agreed(contract_id,amount,payment_mode,payment_date,create_user,create_time,update_user,update_time,remark,status) values (:contractId,:amount,:paymentMode,:paymentDate,:createUser,:createTime,:updateUser,:updateTime,:remark,:status)";
	private static final String PAYMENT_AGREED_UPDATE_UNUSE="update payment_agreed set update_user=:updateUser,update_time=:updateTime,status=0 where contract_id=:contractId";
	private static final String PAYMENT_AGREED_UPDATE="update payment_agreed set amount=:amount,payment_mode=:paymentMode,payment_date=:paymentDate,update_user=:updateUser,update_time=:updateTime,remark=:remark,status=:status where id=:id";
	private static final String PAYMENT_REALITY_SELECT="select * from payment_reality where 1=1 ";
	private static final String PAYMENT_REALITY_INSERT="insert into payment_reality(contract_id,amount,payment_mode,payment_date,create_user,create_time,update_user,update_time,remark,status) values (:contractId,:amount,:paymentMode,:paymentDate,:createUser,:createTime,:updateUser,:updateTime,:remark,:status)";
	
	@Resource
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Override
	public WebPage<Payment> getAgreedAll(Map<String,String> map,Boolean hasPage){
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
					if(key.equals("id")||key.equals("contractId")||key.equals("status")) {
						sb.append(" = "+map.get(key));
					}else {
						sb.append(" like '%"+map.get(key)+"%'");
					}
				}
			}
		}
		sb.append(" order by " + StringUtil.propertyToField(sort) + " " + order);
		if(hasPage) {
			total = namedParameterJdbcTemplate.getJdbcOperations().queryForObject(PAYMENT_AGREED_SELECT+sb.toString(), Long.class);
			sb.append(" limit "+(page-1)*rows+" , " + rows);
		}
		WebPage<Payment> webPage=new WebPage<>(total, page);
		RowMapper<Payment> rowMapper = new BeanPropertyRowMapper<>(Payment.class);
		List<Payment> paymentList = namedParameterJdbcTemplate.query(PAYMENT_AGREED_SELECT+sb.toString(), rowMapper);
		webPage.setRows(paymentList);
		return webPage;
		
		
		
		
		
		
//		Map<String,Integer> paramMap=new HashMap<String,Integer>();
//		paramMap.put("contractId", contractId);
//		RowMapper<Payment> rowMapper = new BeanPropertyRowMapper<>(Payment.class);
//		return namedParameterJdbcTemplate.query(PAYMENT_AGREED_BY_CONTRACT_ID_SELECT, paramMap,rowMapper);
	}
	@Override
	public WebPage<Payment> getRealityAll(Map<String,String> map,Boolean hasPage){
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
					if(key.equals("id")||key.equals("contractId")||key.equals("status")) {
						sb.append(" = "+map.get(key));
					}else {
						sb.append(" like '%"+map.get(key)+"%'");
					}
				}
			}
		}
		sb.append(" order by " + StringUtil.propertyToField(sort) + " " + order);
		if(hasPage) {
			total = namedParameterJdbcTemplate.getJdbcOperations().queryForObject(PAYMENT_REALITY_SELECT+sb.toString(), Long.class);
			sb.append(" limit "+(page-1)*rows+" , " + rows);
		}
		WebPage<Payment> webPage=new WebPage<>(total, page);
		RowMapper<Payment> rowMapper = new BeanPropertyRowMapper<>(Payment.class);
		List<Payment> paymentList = namedParameterJdbcTemplate.query(PAYMENT_REALITY_SELECT+sb.toString(), rowMapper);
		webPage.setRows(paymentList);
		return webPage;
		
		
		
		
//		Map<String,Integer> paramMap=new HashMap<String,Integer>();
//		paramMap.put("contractId", contractId);
//		RowMapper<Payment> rowMapper = new BeanPropertyRowMapper<>(Payment.class);
//		return namedParameterJdbcTemplate.query(PAYMENT_REALITY_BY_CONTRACT_ID_SELECT, paramMap,rowMapper);
	}
	@Override
	public Boolean saveAgreedBatch(List<Payment> paymentAgreedList) {
		if(paymentAgreedList.size()>0) {
			namedParameterJdbcTemplate.batchUpdate(PAYMENT_AGREED_INSERT, SqlParameterSourceUtils.createBatch(paymentAgreedList.toArray()));
		}
		return true;
	}
	@Override
	public Boolean saveRealityBatch(List<Payment> paymentRealityList) {
		if(paymentRealityList.size()>0) {
			namedParameterJdbcTemplate.batchUpdate(PAYMENT_REALITY_INSERT, SqlParameterSourceUtils.createBatch(paymentRealityList.toArray()));
		}
		return true;
	}
	@Override
	public Integer updateAgreedUnuse(Payment payment) {
		SqlParameterSource paramMap = new BeanPropertySqlParameterSource(payment);
		return namedParameterJdbcTemplate.update(PAYMENT_AGREED_UPDATE_UNUSE, paramMap);
	}
	@Override
	public Boolean updateAgreedBatch(List<Payment> paymentList) {
		if(paymentList.size()>0) {
			namedParameterJdbcTemplate.batchUpdate(PAYMENT_AGREED_UPDATE, SqlParameterSourceUtils.createBatch(paymentList.toArray()));
		}
		return true;
	}
}
