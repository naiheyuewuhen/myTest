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

import com.manage.dao.ContractFileDao;
import com.manage.model.ContractFile;
import com.manage.model.WebPage;
import com.manage.util.StringUtil;

@Repository("contractFileDao")
public class ContractFileDaoImpl implements ContractFileDao {

	private static final String CONTRACT_FILE_SELECT="select * from contract_file where 1=1 ";
	private static final String CONTRACT_FILE_INSERT="insert into contract_file(contract_id,upload_file_id,file_name,file_url,create_time,file_type,status) values (:contractId,:uploadFileId,:fileName,:fileUrl,:createTime,:fileType,:status)";
	private static final String CONTRACT_FILE_UPDATE_UNUSE="update contract_file set status=0 where contract_id=:contractId";
	private static final String CONTRACT_FILE_UPDATE="update contract_file set status=:status where id=:id";
	
	@Resource
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	@Override
	public WebPage<ContractFile> getAll(Map<String,String> map,Boolean hasPage){
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
					if(key.equals("id")||key.equals("contractId")||key.equals("uploadFileId")||key.equals("status")) {
						sb.append(" = "+map.get(key));
					}else {
						sb.append(" like '%"+map.get(key)+"%'");
					}
				}
			}
		}
		sb.append(" order by " + StringUtil.propertyToField(sort) + " " + order);
		if(hasPage) {
			total = namedParameterJdbcTemplate.getJdbcOperations().queryForObject(CONTRACT_FILE_SELECT+sb.toString(), Long.class);
			sb.append(" limit "+(page-1)*rows+" , " + rows);
		}
		WebPage<ContractFile> webPage=new WebPage<>(total, page);
		RowMapper<ContractFile> rowMapper = new BeanPropertyRowMapper<>(ContractFile.class);
		List<ContractFile> contractFileList = namedParameterJdbcTemplate.query(CONTRACT_FILE_SELECT+sb.toString(), rowMapper);
		webPage.setRows(contractFileList);
		return webPage;
//		Map<String,Integer> paramMap=new HashMap<String,Integer>();
//		paramMap.put("contractId", contractId);
//		RowMapper<ContractFile> rowMapper = new BeanPropertyRowMapper<>(ContractFile.class);
//		return namedParameterJdbcTemplate.query(CONTRACT_FILE_SELECT, paramMap, rowMapper);
	}
	@Override
	public Boolean saveBatch(List<ContractFile> contractFileList) {
		if(contractFileList.size()>0) {
			namedParameterJdbcTemplate.batchUpdate(CONTRACT_FILE_INSERT, SqlParameterSourceUtils.createBatch(contractFileList.toArray()));
		}
		return true;
	}
	@Override
	public Integer updateUnuse(Integer contractId) {
		Map<String,Integer> map=new HashMap<String,Integer>();
		map.put("contractId", contractId);
		return namedParameterJdbcTemplate.update(CONTRACT_FILE_UPDATE_UNUSE, map);
	}
	@Override
	public Boolean updateBatch(List<ContractFile> contractFileList) {
		if(contractFileList.size()>0) {
			namedParameterJdbcTemplate.batchUpdate(CONTRACT_FILE_UPDATE, SqlParameterSourceUtils.createBatch(contractFileList.toArray()));
		}
		return true;
	}
}
