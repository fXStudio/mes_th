package com.qm.th.beans;

/**
 * 特殊KIN号
 * 
 * @author GaoHF
 */
/**
 * 
 * 
 * @author GaoHF
 */
public class SpecKin {
	/** 唯一标识F */
	private String id;
	/** KIN号 */
	private String kin;
	/** VIN号 */
	private String vin;
	/** 总装上线时间 */
	private String dwbegin;
	/** 焊装上线时间 */
	private String dabegin;

	public String getKin() {
		return kin;
	}

	public void setKin(String kin) {
		this.kin = kin;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getDwbegin() {
		return dwbegin;
	}

	public void setDwbegin(String dwbegin) {
		if (dwbegin != null)
			dwbegin = dwbegin.replaceAll("\\.[0-9]+$", "");
		this.dwbegin = dwbegin;
	}

	public String getDabegin() {
		return dabegin;
	}

	public void setDabegin(String dabegin) {
		if (dabegin != null)
			dabegin = dabegin.replaceAll("\\.[0-9]+$", "");
		this.dabegin = dabegin;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
