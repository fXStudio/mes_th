package mes.pm.bean;

import java.util.Date;

/**
 * 实体Bean用于封装配送信息的确认单
 * 
 * @author YuanPeng
 *
 */
public class DistributionAccept {
	/**
	 * 配送确认单序号
	 */
	private int id;
	/**
	 * 配送单ID
	 */
	private int DisDocId;
	/**
	 * 状态（未处理/已处理）
	 */
	private int state; 
	/**
	 * 响应用户ID
	 */
	private int responseUID; 
	/**
	 * 配送单物料标示
	 */
	private String materiel; 
	/**
	 * 请求时间
	 */
	private Date requestDate;
	/**
	 * 响应时间
	 */
	private Date responseDate;
	/**
	 * 响应生产单元号
	 */
	private int targetProUnit;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getDisDocId() {
		return DisDocId;
	}
	public void setDisDocId(int disDocId) {
		DisDocId = disDocId;
	}
	public int getResponseUID() {
		return responseUID;
	}
	public void setResponseUID(int responseUID) {
		this.responseUID = responseUID;
	}
	public String getMateriel() {
		return materiel;
	}
	public void setMateriel(String materiel) {
		this.materiel = materiel;
	}
	public Date getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}
	public Date getResponseDate() {
		return responseDate;
	}
	public void setResponseDate(Date responseDate) {
		this.responseDate = responseDate;
	}
	public int getTargetProUnit() {
		return targetProUnit;
	}
	public void setTargetProUnit(int targetProUnit) {
		this.targetProUnit = targetProUnit;
	}

}
