package com.qm.mes.pm.bean;
/**
 * 工艺操作项文件
 * 
 * @author YuanPeng
 *
 */
public class TechItemFile {
	/**
	 * 序号
	 */
	private int id;
	/**
	 * 工艺操作项序号
	 */
	private int techDocItemId;
	/**
	 * 路径+名称
	 */
	private String pathName;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTechDocItemId() {
		return techDocItemId;
	}
	public void setTechDocItemId(int techDocItemId) {
		this.techDocItemId = techDocItemId;
	}
	public String getPathName() {
		return pathName;
	}
	public void setPathName(String pathName) {
		this.pathName = pathName;
	}
	
}
