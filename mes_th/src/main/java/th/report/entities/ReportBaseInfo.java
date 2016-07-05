package th.report.entities;

import java.util.HashMap;

public class ReportBaseInfo {
	private String dabegin = "";
	private String daseqa = "";
	private int carno = 1;
	private String tfassId = "";
	private int pageNo;
	private String chassisNumber = "";
	private HashMap<String, String> hmVin = new HashMap<String, String>();

	public String getVinByCarType(String key) {
		return hmVin.get(key);
	}

	public void putVinMap2CarType(String key, String value) {
		hmVin.put(key, value);
	}

	public String getChassisNumber() {
		return chassisNumber;
	}

	public void setChassisNumber(String chassisNumber) {
		this.chassisNumber = chassisNumber;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public String getTfassId() {
		return tfassId;
	}

	public void setTfassId(String tfassId) {
		this.tfassId = tfassId;
	}

	public Integer getCarno() {
		return carno;
	}

	public void setCarno(Integer carno) {
		this.carno = carno;
	}

	public String getDabegin() {
		return dabegin;
	}

	public void setDabegin(String dabegin) {
		this.dabegin = dabegin;
	}

	public String getDaseqa() {
		return daseqa;
	}

	public void setDaseqa(String daseqa) {
		this.daseqa = daseqa;
	}
}
