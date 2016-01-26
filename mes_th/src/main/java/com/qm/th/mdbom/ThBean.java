package com.qm.th.mdbom;

/**
 * 天合BOM信息
 * 
 * @author gaohf
 * @version 1.0.1
 * @date 2009-9-24
 */
public class ThBean {
	/** 数据库标识 */
	private int id;
	/** 天合零件号 */
	private String ctfass;
	/** 天合零件数量 */
	private String nTFASSNum;
	/** 大众主零件号 */
	private String cVWMainPart;
	/** 大众主零件数量 */
	private String cVWMainPartQuan;
	/** 大众主零件种类数 */
	private String cVWMainPartType;
	/** 大众子零件号 */
	private String cVWSubPart;
	/** 大众子零件数量 */
	private String nVWSubPartQuan;
	/** 大众子零件种类数 */
	private String nVWSubPartType;
	/** 天合零件名称 */
	private String cTFASSName;
	/** 天合零件类型数量 */
	private String cTFASSTypeNo;
	/** 方案号 */
	private String cQADNo;
	/** 是否过度车型 */
	private String cIsTempCar;
	/** 备注 */
	private String cRemark;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCtfass() {
		return ctfass;
	}

	public void setCtfass(String ctfass) {
		this.ctfass = ctfass;
	}

	public String getNTFASSNum() {
		return nTFASSNum;
	}

	public void setNTFASSNum(String num) {
		nTFASSNum = num;
	}

	public String getCVWMainPart() {
		return cVWMainPart;
	}

	public void setCVWMainPart(String mainPart) {
		cVWMainPart = mainPart;
	}

	public String getCVWMainPartQuan() {
		return cVWMainPartQuan;
	}

	public void setCVWMainPartQuan(String mainPartQuan) {
		cVWMainPartQuan = mainPartQuan;
	}

	public String getCVWMainPartType() {
		return cVWMainPartType;
	}

	public void setCVWMainPartType(String mainPartType) {
		cVWMainPartType = mainPartType;
	}

	public String getCVWSubPart() {
		return cVWSubPart;
	}

	public void setCVWSubPart(String subPart) {
		cVWSubPart = subPart;
	}

	public String getNVWSubPartQuan() {
		return nVWSubPartQuan;
	}

	public void setNVWSubPartQuan(String subPartQuan) {
		nVWSubPartQuan = subPartQuan;
	}

	public String getNVWSubPartType() {
		return nVWSubPartType;
	}

	public void setNVWSubPartType(String subPartType) {
		nVWSubPartType = subPartType;
	}

	public String getCTFASSName() {
		return cTFASSName;
	}

	public void setCTFASSName(String name) {
		cTFASSName = name;
	}

	public String getCTFASSTypeNo() {
		return cTFASSTypeNo;
	}

	public void setCTFASSTypeNo(String typeNo) {
		cTFASSTypeNo = typeNo;
	}

	public String getCQADNo() {
		return cQADNo;
	}

	public void setCQADNo(String no) {
		cQADNo = no;
	}

	public String getCIsTempCar() {
		return cIsTempCar;
	}

	public void setCIsTempCar(String isTempCar) {
		cIsTempCar = isTempCar;
	}

	public String getCRemark() {
		return cRemark;
	}

	public void setCRemark(String remark) {
		cRemark = remark;
	}
}
