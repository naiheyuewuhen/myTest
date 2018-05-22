package com.manage.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.manage.dao.FileDao;
import com.manage.model.FileInfo;
import com.manage.service.FileService;

@Service("fileService")
public class FileServiceImpl implements FileService {

	@Resource
	private FileDao fileDao;
	@Override
	public List<FileInfo> save(List<FileInfo> fileInfos) {
		for(FileInfo fileInfo:fileInfos) {
			Integer id = fileDao.save(fileInfo);
			fileInfo.setId(id);
		}
		return fileInfos;
	}

	@Override
	public List<FileInfo> getAll(Map<String, String> map) {
		return fileDao.getAll(map);
	}

}
