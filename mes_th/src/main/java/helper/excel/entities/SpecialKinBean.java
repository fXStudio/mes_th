package helper.excel.entities;

public class SpecialKinBean {
	private String kincode;
	private String enabled;
	private String remark;

	public String getKincode() {
		return kincode;
	}

	public void setKincode(String kincode) {
		this.kincode = kincode;
	}

	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled != null && "1".equals(enabled) ? "1" : "0";
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
