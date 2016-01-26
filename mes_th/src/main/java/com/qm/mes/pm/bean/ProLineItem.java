package com.qm.mes.pm.bean;

/**
 * 生产单元线性内容
 * 
 * @author YuanPeng
 *
 */
public class ProLineItem {
	/**
	 * 序号
	 */
	private int id;
	/**
	 * 生产单元号
	 */
	private int produceUnitId;
	/**
	 * 生产单元名
	 */
	private String prodUnitName;
	/**
	 * 顺序号
	 */
	private int Order;
	/**
	 * 生产单元线性配置号
	 */
	private int LineId;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getProduceUnitId() {
		return produceUnitId;
	}
	public void setProduceUnitId(int produceUnitId) {
		this.produceUnitId = produceUnitId;
	}
	public int getOrder() {
		return Order;
	}
	public void setOrder(int order) {
		Order = order;
	}
	public int getLineId() {
		return LineId;
	}
	public void setLineId(int lineId) {
		LineId = lineId;
	}
	public String getProdUnitName() {
		return prodUnitName;
	}
	public void setProdUnitName(String prodUnitName) {
		this.prodUnitName = prodUnitName;
	}
}
