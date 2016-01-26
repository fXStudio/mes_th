package com.qm.mes.pm.bean;

import java.util.Date;
/**
 * 异常类型异常记录信息
 * @author Xujia
 *
 */
public class ExceptionRecord {
	/**
	 *  序号
	 */
	private int id; 
	/**
	 * 上报用户
	 */
	private int userId; 
	/**
	 * 用户名称
	 */
	private String userName;
	/**
	 * 生产单元
	 */
	private int produceUnitId; 
	/**
	 * 生产单元名
	 */
	private String prodUnitName;
	/**
	 * 关闭用户
	 */
	private int closeuser;
	/**
	 * 上报时间
	 */
	private Date start; 
	/**
	 * String型上报时间
	 */
	private String str_start;
	/**
	 *  关闭时间
	 */
	private Date close;
	/**
	 * 关闭时间
	 */
	private String str_end;
	/**
	 * 异常的描述
	 */
	private String description;  
	/**
	 *  异常恢复的描述
	 */
	private String rediscription; 
	/**
	 *  异常类型(预警/误工)
	 */
	private int exceptionTypeId;
	/**
	 *  异常类型名称
	 */
	private String excepTypeName;
	/**
	 *  异常原因
	 */
	private int exceptionCauseId; 
	/**
	 *  异常原因名称
	 */
	private String excepCauseName;
	/**
	 *   状态标识
	 */
	private int state;         
	/**
	 *   设备号
	 */
	private int deviceid;        
	/**
	 *   设备类型号
	 */
	private int devicetypeid;    
	/**
	 *  关闭、上报异常时差（s）
	 */
	private long timelang;		
	
	
	/**
	 * @return close
	 */
	public Date getClose() {
		return close;
	}
	/**
	 * @param close 要设置的 close
	 */
	public void setClose(Date close) {
		this.close = close;
	}
	/**
	 * @return description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description 要设置的 description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return deviceid
	 */
	public int getDeviceid() {
		return deviceid;
	}
	/**
	 * @param deviceid 要设置的 deviceid
	 */
	public void setDeviceid(int deviceid) {
		this.deviceid = deviceid;
	}
	/**
	 * @return devicetypeid
	 */
	public int getDevicetypeid() {
		return devicetypeid;
	}
	/**
	 * @param devicetypeid 要设置的 devicetypeid
	 */
	public void setDevicetypeid(int devicetypeid) {
		this.devicetypeid = devicetypeid;
	}
	/**
	 * @return exceptionCauseId
	 */
	public int getExceptionCauseId() {
		return exceptionCauseId;
	}
	/**
	 * @param exceptionCauseId 要设置的 exceptionCauseId
	 */
	public void setExceptionCauseId(int exceptionCauseId) {
		this.exceptionCauseId = exceptionCauseId;
	}
	/**
	 * @return exceptionTypeId
	 */
	public int getExceptionTypeId() {
		return exceptionTypeId;
	}
	/**
	 * @param exceptionTypeId 要设置的 exceptionTypeId
	 */
	public void setExceptionTypeId(int exceptionTypeId) {
		this.exceptionTypeId = exceptionTypeId;
	}
	/**
	 * @return id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id 要设置的 id
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return produceUnitId
	 */
	public int getProduceUnitId() {
		return produceUnitId;
	}
	/**
	 * @param produceUnitId 要设置的 produceUnitId
	 */
	public void setProduceUnitId(int produceUnitId) {
		this.produceUnitId = produceUnitId;
	}
	/**
	 * @return rediscription
	 */
	public String getRediscription() {
		return rediscription;
	}
	/**
	 * @param rediscription 要设置的 rediscription
	 */
	public void setRediscription(String rediscription) {
		this.rediscription = rediscription;
	}
	/**
	 * @return start
	 */
	public Date getStart() {
		return start;
	}
	/**
	 * @param start 要设置的 start
	 */
	public void setStart(Date start) {
		this.start = start;
	}
	/**
	 * @return state
	 */
	public int getState() {
		return state;
	}
	/**
	 * @param state 要设置的 state
	 */
	public void setState(int state) {
		this.state = state;
	}
	
	/**
	 * @return closeuser
	 */
	public int getCloseuser() {
		return closeuser;
	}
	/**
	 * @param closeuser 要设置的 closeuser
	 */
	public void setCloseuser(int closeuser) {
		this.closeuser = closeuser;
	}
	/**
	 * @return userId
	 */
	public int getUserId() {
		return userId;
	}
	/**
	 * @param userId 要设置的 userId
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getProdUnitName() {
		return prodUnitName;
	}
	public void setProdUnitName(String prodUnitName) {
		this.prodUnitName = prodUnitName;
	}
	public String getExcepTypeName() {
		return excepTypeName;
	}
	public void setExcepTypeName(String excepTypeName) {
		this.excepTypeName = excepTypeName;
	}
	public String getExcepCauseName() {
		return excepCauseName;
	}
	public void setExcepCauseName(String excepCauseName) {
		this.excepCauseName = excepCauseName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getStr_start() {
		return str_start;
	}
	public void setStr_start(String str_start) {
		this.str_start = str_start;
	}
	public String getStr_end() {
		return str_end;
	}
	public void setStr_end(String str_end) {
		this.str_end = str_end;
	}
	public long getTimelang() {
		return timelang;
	}
	public void setTimelang(long timelang) {
		this.timelang = timelang;
	}
	
	

}
