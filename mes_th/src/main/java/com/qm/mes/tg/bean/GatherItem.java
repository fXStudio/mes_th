package com.qm.mes.tg.bean;

public class GatherItem {
	private int id;//序号
	private int order;//顺序
	//private String name;//属性名称，与规则中的名称重复去掉
	//private String desc;//属性描述
	private int materielruleId;//物料验证规则id
	private int gatherId;//采集点序号
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
//	public String getName() {
//		return name;
//	}
//	public void setName(String name) {
//		this.name = name;
//	}
//	public String getDesc() {
//		return desc;
//	}
//	public void setDesc(String desc) {
//		this.desc = desc;
//	}
	public int getGatherId() {
		return gatherId;
	}
	public void setGatherId(int gatherId) {
		this.gatherId = gatherId;
	}
	public int getMaterielruleId() {
		return materielruleId;
	}
	public void setMaterielruleId(int materielruleId) {
		this.materielruleId = materielruleId;
	}
}
