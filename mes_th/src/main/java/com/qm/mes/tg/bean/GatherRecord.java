package com.qm.mes.tg.bean;

import java.util.Date;

public class GatherRecord {
	private int id;// 序号
	private int gatherId;// 采集点序号
	private String materielName;// 主物料名称
	private String materielValue;// 主物料值
	private int userId;// 用户id
	private Date date;// 生成时间

	private String str; // 一个中间量
	/**
	 * modify by yld
	 */
	private String datestr;// 日期对象的字符描述值

	public String getDatestr() {
		return datestr;
	}

	public void setDatestr(String datestr) {
		this.datestr = datestr;
	}

	/**
	 * end of modify by yld
	 * 
	 * @return
	 */
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getGatherId() {
		return gatherId;
	}

	public void setGatherId(int gatherId) {
		this.gatherId = gatherId;
	}

	public String getMaterielValue() {
		return materielValue;
	}

	public void setMaterielValue(String materielValue) {
		this.materielValue = materielValue;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getMaterielName() {
		return materielName;
	}

	public void setMaterielName(String materielname) {
		this.materielName = materielname;
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}
}
