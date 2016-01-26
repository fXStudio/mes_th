package com.qm.mes.pm.bean;

/**
 * 装配子件信息
 * @author YuanPeng
 *
 */
public class AssDocItem {
	/**
	 * 装配指示项序号
	 */
	private int id;
	/**
	 * 装配指示单号
	 */
	private int AssDocId;
	/**
	 * 名称
	 */
	private String name; 
	/**
	 * 子件标示
	 */
	private String code; 
	/**
	 * 子件描述
	 */
	private String description;
	
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getAssDocId() {
		return AssDocId;
	}
	public void setAssDocId(int assDocId) {
		AssDocId = assDocId;
	}

}
