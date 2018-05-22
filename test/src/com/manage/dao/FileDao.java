package com.manage.dao;

import java.util.List;
import java.util.Map;

import com.manage.model.FileInfo;

public interface FileDao {

	Integer save(FileInfo fileInfo);

	List<FileInfo> getAll(Map<String, String> map);
}
