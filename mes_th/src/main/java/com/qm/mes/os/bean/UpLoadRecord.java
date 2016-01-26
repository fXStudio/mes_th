package com.qm.mes.os.bean;
import java.util.Date;

public class UpLoadRecord {
	/**
	  * 序号
	  */
	private int id;
	/**
	  * 动作发生时间
	  */
	private Date upLoadDatime;
	/**
	  * 发布计划用户名
	  */
	private String userName;
	/**
	  * 版本号
	  */
	private String versionCode;
	/**
	  * 发布/取消发布
	  */
	private int upload;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getUpLoadDatime() {
		return upLoadDatime;
	}
	public void setUpLoadDatime(Date upLoadDatime) {
		this.upLoadDatime = upLoadDatime;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getVersionCode() {
		return versionCode;
	}
	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}
	public int getUpload() {
		return upload;
	}
	public void setUpload(int upload) {
		this.upload = upload;
	}
	
}
