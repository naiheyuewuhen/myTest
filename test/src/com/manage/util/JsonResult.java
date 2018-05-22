package com.manage.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.manage.model.Payment;

public class JsonResult {
	
	private static final String PAYMENT_AGREED_UPDATE="update payment_agreed set contract_id=:contractId,amount=:amount,payment_mode=:paymentMode where id=:id ";
	
//	private static NamedParameterJdbcTemplate namedParameterJdbcTemplate;
//	{
//		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml"); 
//		namedParameterJdbcTemplate=(NamedParameterJdbcTemplate) ac.getBean("namedParameterJdbcTemplate");
//	}
	
	public static String object2Json(Object object) throws Exception {
//		JsonResultInfo jsonResultInfo = new JsonResultInfo();
//		jsonResultInfo.setSuccess(true);
//		jsonResultInfo.setData(object);
//		Gson gson = new Gson();
//		return "{\"success\":true,\"data\":"+gson.toJson(object)+"}";
//		return jsonResultInfo;
		ObjectMapper mapper = new ObjectMapper();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("success", true);
		map.put("data", object);
		return mapper.writeValueAsString(map);
	}
	public static int[] updateBatchTest(List<Payment> paymentAgreedList) {
		@SuppressWarnings("resource")
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml"); 
		NamedParameterJdbcTemplate namedParameterJdbcTemplate=(NamedParameterJdbcTemplate) ac.getBean("namedParameterJdbcTemplate");
		return namedParameterJdbcTemplate.batchUpdate(PAYMENT_AGREED_UPDATE, SqlParameterSourceUtils.createBatch(paymentAgreedList.toArray()));
	}

}
