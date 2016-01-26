package com.qm.mes.os.bean;
import java.util.*;
 /**
  * 
  * @author  谢静天 2009-5-13
  *
  */
public class Plan {
	/**
	  *作业计划编号
	  */
	private  int id ; 
	/**
	  *计划日期
	  */
	private Date planDate; 
	/**
	  *生产日期
	  */
	private Date produceDate;  
	/**
	  *订单号
	  */
	private  String orderFormId;  
	/**
	  *计划批次号
	  */
	private  int planGroupId; 
	/**
	  *产品类别标识（如车型码）
	  */
	private  String produceType;  
	/**
	  *产品名称
	  */
	private String produceName ;
	/**
	  *产品标识（如底盘号、批次号）
	  */
	private String produceMarker ; 
	/**
	  *生产单元
	  */
	private int produnitid; 
	/**
	  *班组
	  */
	private String workTeam; 
	/**
	  *班次
	  */
	private String workOrder;  
	/**
	  *数量
	  */
	private int amount; 
	/**
	  *版本号
	  */
	private String versioncode;  
	/**
	  *发布
	  */
	private int upload;  
	/**
	  *计划顺序号
	  */
	private int planOrder;
	/**
	  *描述
	  */
	private String description;
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getPlanOrder() {
		return planOrder;
	}
	public void setPlanOrder(int planOrder) {
		this.planOrder = planOrder;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getPlanDate() {
		return planDate;
	}
	public void setPlanDate(Date planDate) {
		this.planDate = planDate;
	}
	public Date getProduceDate() {
		return produceDate;
	}
	public void setProduceDate(Date produceDate) {
		this.produceDate = produceDate;
	}
	public String getOrderFormId() {
		return orderFormId;
	}
	public void setOrderFormId(String orderFormId) {
		this.orderFormId = orderFormId;
	}
	public int getPlanGroupId() {
		return planGroupId;
	}
	public void setPlanGroupId(int planGroupId) {
		this.planGroupId = planGroupId;
	}
	
	public String getProduceType() {
		return produceType;
	}
	public void setProduceType(String produceType) {
		this.produceType = produceType;
	}

	public String getProduceName() {
		return produceName;
	}
	public void setProduceName(String produceName) {
		this.produceName = produceName;
	}
	public String getProduceMarker() {
		return produceMarker;
	}
	public void setProduceMarker(String produceMarker) {
		this.produceMarker = produceMarker;
	}
	public int getProdunitid() {
		return produnitid;
	}
	public void setProdunitid(int produnitid) {
		this.produnitid = produnitid;
	}
	public String getWorkTeam() {
		return workTeam;
	}
	public void setWorkTeam(String workTeam) {
		this.workTeam = workTeam;
	}
	public String getWorkOrder() {
		return workOrder;
	}
	public void setWorkOrder(String workOrder) {
		this.workOrder = workOrder;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getVersioncode() {
		return versioncode;
	}
	public void setVersioncode(String versioncode) {
		this.versioncode = versioncode;
	}
	public int getUpload() {
		return upload;
	}
	public void setUpload(int upload) {
		this.upload = upload;
	}


}
