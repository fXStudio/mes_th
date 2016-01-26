package com.qm.th.pz.bean;

public class JZConfigure {
	String cSEQNo_A; //总装顺序号
	String cVinCode; //vin码
	String cQADNo;  //天合总成号
	String cCarType; // 车型
	int index;
	String tt ;
	public String getCSEQNo_A() {
		return cSEQNo_A;
	}
	public void setCSEQNo_A(String no_A) {
		cSEQNo_A = no_A;
	}
	public String getCVinCode() {
		return cVinCode;
	}
	public void setCVinCode(String vinCode) {
		cVinCode = vinCode;
	}
	public String getCQADNo() {
		return cQADNo;
	}
	public void setCQADNo(String no) {
		cQADNo = no;
	}
	public String getCCarType() {
		return cCarType;
	}
	public void setCCarType(String carType) {
		cCarType = carType;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getTt() {
		return tt;
	}
	public void setTt(String tt) {
		this.tt = tt;
	}

}
