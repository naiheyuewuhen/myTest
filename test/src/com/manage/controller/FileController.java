package com.manage.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.aspectj.util.FileUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.manage.model.FileInfo;
import com.manage.service.FileService;
import com.manage.util.JsonResult;

@Controller
@RequestMapping("/file")
public class FileController {
	@Resource
	private FileService fileService;
	@ResponseBody()
	@RequestMapping(value="/upload",produces="application/json; charset=utf-8")
	public String upload(@RequestParam("file") MultipartFile[] files,HttpSession session) throws Exception {
		Date dateTime=new Date();
		List<FileInfo> fileInfos = new ArrayList<FileInfo>();
		for(MultipartFile item:files) {
			if(!item.isEmpty()) {
				String fileName=item.getOriginalFilename();//获取文件名
				String fileSaveName=DigestUtils.md5DigestAsHex(String.valueOf(System.currentTimeMillis()+Math.random()).getBytes());
				String pathName=session.getServletContext().getRealPath("/")+"upload/"+fileSaveName;//获取文件存放路径
//				String StringUrl=session.getServletContext().getContextPath()+"/upload/"+fileName;
				String StringUrl="/upload/"+fileSaveName;
				File file=new File(pathName);
				item.transferTo(file);
				FileInfo fileInfo=new FileInfo();
				fileInfo.setFileName(fileName);
				fileInfo.setCreateTime(dateTime);
				fileInfo.setFileUrl(StringUrl);
				fileInfo.setStatus(1);
				fileInfos.add(fileInfo);
			}
		}
		fileService.save(fileInfos);
		return JsonResult.object2Json(fileInfos);
	}
	@RequestMapping("/download")
	public ResponseEntity<byte[]> download(@RequestParam Integer id,HttpSession session) throws IOException{
		Map<String, String> map=new HashMap<>();
		map.put("id", id.toString());
		List<FileInfo> fileInfos=fileService.getAll(map);
		if(fileInfos.size()>0) {
			FileInfo fileInfo=fileInfos.get(0);
			String pathName=session.getServletContext().getRealPath(fileInfo.getFileUrl());//获取文件存放路径
			
			File file=new File(pathName);
			String fileName=new String(fileInfo.getFileName().getBytes("UTF-8"),"ISO-8859-1");//解决中文乱码
			HttpHeaders headers=new HttpHeaders();
			headers.setContentDispositionFormData("attachment", fileName);
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			return new ResponseEntity<byte[]>(FileUtil.readAsByteArray(file),headers,HttpStatus.CREATED);
			/*<a href="${pageContext.request.contextPath }/download.do?line.jpg">下载</a>*/
		}else {
			return null;
		}
	}

}
