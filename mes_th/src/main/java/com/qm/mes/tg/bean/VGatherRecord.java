/*
 * create by cp 
 * view gahterRecord
 * 20080725
 */
package com.qm.mes.tg.bean;

import java.util.Date;

public class VGatherRecord {

	private int id;//序号
	//private int gatherId;//采集点序号
	private String gatherIdName;//采集点名称
	private String materielName;//主物料名称
	private String materielValue;//主物料值
	private int userId;//用户id
	private Date date;//生成时间
	private String datestr;//日期对象的字符描述值

	 public String getDatestr() {
	  return datestr;
	 }
	 public void setDatestr(String datestr) {
	  this.datestr = datestr;
	 }
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	/*
	public int getGatherId() {
		return gatherId;
	}
	public void setGatherId(int gatherId) {
		this.gatherId = gatherId;
	}
	*/

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
	public String getGatherIdName() {
		return gatherIdName;
	}
	public void setGatherIdName(String gatherIdName) {
		this.gatherIdName = gatherIdName;
	}
}
