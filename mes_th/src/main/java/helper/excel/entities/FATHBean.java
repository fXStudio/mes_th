package helper.excel.entities;

import java.util.Date;

/**
 * 数据实体类
 */
public class FATHBean {
	private String id;
	private String status;
	private String seq;
	private Date cp5adate;
	private Date cp5atime;
	private String model;
	private String knr;
	private String color;
	private String colorDesc;
	private String type;
	private String chassi;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public Date getCp5adate() {
		return cp5adate;
	}

	public void setCp5adate(Date cp5adate) {
		this.cp5adate = cp5adate;
	}

	public Date getCp5atime() {
		return cp5atime;
	}

	public void setCp5atime(Date cp5atime) {
		this.cp5atime = cp5atime;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getKnr() {
		return knr;
	}

	public void setKnr(String knr) {
		this.knr = knr;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getColorDesc() {
		return colorDesc;
	}

	public void setColorDesc(String colorDesc) {
		this.colorDesc = colorDesc;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getChassi() {
		return chassi;
	}

	public void setChassi(String chassi) {
		this.chassi = chassi;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}