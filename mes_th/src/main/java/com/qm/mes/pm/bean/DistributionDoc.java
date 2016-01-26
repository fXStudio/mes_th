package com.qm.mes.pm.bean;

import java.util.Date;
import java.util.List;
/**
 * 实体Bean用于封装配送指示单的实体信息
 * 
 * @author YuanPeng
 *
 */
public class DistributionDoc {
	/**
	 * 装配送指示单序号
	 */
	private int id ; 
	/**
	 * 名称
	 */
	private String name; 
	/**
	 * 触发请求的生产单元
	 */
	private int request_proUnit; 
	/**
	 * 请求生产单元名
	 */
	private String req_proUnitName;
	/**
	 * 响应请求的生产单元
	 */
	private int response_proUnit; 
	/**
	 * 响应生产单元名
	 */
	private String res_proUnitName;
	/**
	 * 需要接收物料的生产单元
	 */
	private int target_proUnit; 
	/**
	 * 接收物料生产单元名
	 */
	private String tar_proUnitName;
	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * 更新时间
	 */
	private Date upDate;
	/**
	 * 创建用户ID
	 */
	private int CreateUID;
	/**
	 * 创建用户名
	 */
	private String createUName;
	/**
	 * 更新用户ID
	 */
	private int UpdateUID;
	/**
	 * 更新用户名
	 */
	private String updateUName;
	/**
	 * 响应BOM组件标示
	 */
	private String materielType; 
	/**
	 * 配送信息子项
	 */
	private List<DistriItem> DistriItems; 
	/**
	 * 功能描述信息
	 */
	private String description; 
	/**
	 * BOM序号
	 */
	private int bomId;
	
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
	public int getRequest_proUnit() {
		return request_proUnit;
	}
	public void setRequest_proUnit(int request_proUnit) {
		this.request_proUnit = request_proUnit;
	}
	public int getResponse_proUnit() {
		return response_proUnit;
	}
	public void setResponse_proUnit(int response_proUnit) {
		this.response_proUnit = response_proUnit;
	}
	public int getTarget_proUnit() {
		return target_proUnit;
	}
	public void setTarget_proUnit(int target_proUnit) {
		this.target_proUnit = target_proUnit;
	}
	public String getMaterielType() {
		return materielType;
	}
	public void setMaterielType(String materielType) {
		this.materielType = materielType;
	}
	public List<DistriItem> getDistriItems() {
		return DistriItems;
	}
	public void setDistriItems(List<DistriItem> distriItems) {
		DistriItems = distriItems;
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
	public int getBomId() {
		return bomId;
	}
	public void setBomId(int bomId) {
		this.bomId = bomId;
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
	public String getReq_proUnitName() {
		return req_proUnitName;
	}
	public void setReq_proUnitName(String req_proUnitName) {
		this.req_proUnitName = req_proUnitName;
	}
	public String getRes_proUnitName() {
		return res_proUnitName;
	}
	public void setRes_proUnitName(String res_proUnitName) {
		this.res_proUnitName = res_proUnitName;
	}
	public String getTar_proUnitName() {
		return tar_proUnitName;
	}
	public void setTar_proUnitName(String tar_proUnitName) {
		this.tar_proUnitName = tar_proUnitName;
	}

}
