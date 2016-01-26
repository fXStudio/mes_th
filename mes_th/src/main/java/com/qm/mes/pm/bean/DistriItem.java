package com.qm.mes.pm.bean;
/**
 * 
 * 实体Bean用于封装配送信息子项的实体信息
 * 
 * @author YuanPeng
 *
 */
public class DistriItem {
	/**
	 * 配送指示项序号
	 */
	private int id; 
	/**
	 * 名称
	 */
	private String name; 
	/**
	 * 物料标示
	 */
	private String matitem; 
	/**
	 * 数量
	 */
	private int count; 
	/**
	 * 配送指示单号
	 */
	private int DistributionDocId;
	/**
	 * 当前设备的功能描述
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
	public String getMatitem() {
		return matitem;
	}
	public void setMatitem(String matitem) {
		this.matitem = matitem;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getDistributionDocId() {
		return DistributionDocId;
	}
	public void setDistributionDocId(int distributionDocId) {
		DistributionDocId = distributionDocId;
	}

}
