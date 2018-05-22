package com.manage.model;

import java.util.ArrayList;
import java.util.List;

public class ModuleInfo {

	private Integer id;
	private String name;
	private String text;
	private String url;
	private Integer level;
	private Integer fatherId;
	private String type;
	private String useImg;
	private Integer sortNum;
	private Integer status;
	private List<ModuleInfo> children = new ArrayList<ModuleInfo>();
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public Integer getFatherId() {
		return fatherId;
	}
	public void setFatherId(Integer fatherId) {
		this.fatherId = fatherId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUseImg() {
		return useImg;
	}
	public void setUseImg(String useImg) {
		this.useImg = useImg;
	}
	public Integer getSortNum() {
		return sortNum;
	}
	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public List<ModuleInfo> getChildren() {
		return children;
	}
	public void setChildren(List<ModuleInfo> children) {
		this.children = children;
	}
}
