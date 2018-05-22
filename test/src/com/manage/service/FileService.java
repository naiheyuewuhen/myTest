package com.manage.service;

import java.util.List;
import java.util.Map;

import com.manage.model.FileInfo;

public interface FileService {

	List<FileInfo> save(List<FileInfo> fileInfos);

	List<FileInfo> getAll(Map<String, String> map);
}
