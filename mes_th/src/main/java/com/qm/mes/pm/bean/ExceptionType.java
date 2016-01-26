package com.qm.mes.pm.bean;

/**
 * 异常类型
 * @author Xujia
 *
 */
public class ExceptionType {

	/**
	 *  异常类型序号
	 */
	private int id;
	/**
	 * 名称
	 */
	private String name; 
	/**
	 * 状态（停用/启用）
	 */
	private int state; 
	/**
	 * 系统内置标示（内置的不允许删除）
	 */
	private int sysdefault; 
	
	
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
	 * @return name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name 要设置的 name
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @return sysdefault
	 */
	public int getSysdefault() {
		return sysdefault;
	}
	/**
	 * @param sysdefault 要设置的 sysdefault
	 */
	public void setSysdefault(int sysdefault) {
		this.sysdefault = sysdefault;
	}
	
	

}
