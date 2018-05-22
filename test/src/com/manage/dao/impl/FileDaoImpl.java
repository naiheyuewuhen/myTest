package com.manage.dao.impl;

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

import com.manage.dao.FileDao;
import com.manage.model.FileInfo;
import com.manage.util.StringUtil;

@Repository("fileDao")
public class FileDaoImpl implements FileDao {

	private static final String FILE_ALL_SELECT="select * from upload_file where 1=1 ";
	private static final String FILE_INSERT="insert into upload_file(file_name,file_url,create_time,status) values (:fileName,:fileUrl,:createTime,:status)";
	
	@Resource
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	@Override
	public Integer save(FileInfo fileInfo) {
		SqlParameterSource paramMap = new BeanPropertySqlParameterSource(fileInfo);
		KeyHolder keyHolder= new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update(FILE_INSERT, paramMap,keyHolder);
		Integer id = keyHolder.getKey().intValue();//获得主键值
		return id;
	}

	@Override
	public List<FileInfo> getAll(Map<String, String> map) {
		StringBuilder sb = new StringBuilder();
		for(String key:map.keySet()) {
			if(!map.get(key).isEmpty()) {
				sb.append(" and "+StringUtil.propertyToField(key));
				if(key.equals("id")||key.equals("status")) {
					sb.append(" = "+map.get(key));
				}else {
					sb.append(" like '%"+map.get(key)+"%'");
				}
			}
		}
		RowMapper<FileInfo> rowMapper = new BeanPropertyRowMapper<FileInfo>(FileInfo.class);
		List<FileInfo> fileInfoList = namedParameterJdbcTemplate.query(FILE_ALL_SELECT+sb.toString(), rowMapper);
		return fileInfoList;
	}

}
