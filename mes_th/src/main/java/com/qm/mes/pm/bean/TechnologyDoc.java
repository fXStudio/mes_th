package com.qm.mes.pm.bean;

import java.util.Date;
import java.util.List;
/**
 * 实体Bean用于工艺说明书的实体信息
 * 
 * @author YuanPeng
 *
 */
public class TechnologyDoc {
	/**
	 * 序号
	 */
	private int id;
	/**
	 * 名称
	 */
	private String name; 
	/**
	 * 产品类别标示
	 */
	private String materiel;
	/**
	 * 详细内容
	 */
	private List<TechDocItem> contents; 
	/**
	 * 工艺说明书的简要描述
	 */
	private String description; 
	/**
	 * 创建用户序号
	 */
	private int CreateUID;
	/**
	 * 创建用户名
	 */
	private String createUName;
	/**
	 * 最后更新用户序号
	 */
	private int UpdateUID;
	/**
	 * 最后更新用户名
	 */
	private String updateUName;
	/**
	 * 创建日期
	 */
	private Date createDate;
	/**
	 * 最后更新日期
	 */
	private Date upDate;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<TechDocItem> getContents() {
		return contents;
	}
	public void setContents(List<TechDocItem> contents) {
		this.contents = contents;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getCreateUID() {
		return CreateUID;
	}
	public void setCreateUID(int createUID) {
		CreateUID = createUID;
	}
	public int getUpdateUID() {
		return UpdateUID;
	}
	public void setUpdateUID(int updateUID) {
		UpdateUID = updateUID;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getUpDate() {
		return upDate;
	}
	public void setUpDate(Date upDate) {
		this.upDate = upDate;
	}
	public String getCreateUName() {
		return createUName;
	}
	public void setCreateUName(String createUName) {
		this.createUName = createUName;
	}
	public String getUpdateUName() {
		return updateUName;
	}
	public void setUpdateUName(String updateUName) {
		this.updateUName = updateUName;
	}
	public String getMateriel() {
		return materiel;
	}
	public void setMateriel(String materiel) {
		this.materiel = materiel;
	}

	
}
