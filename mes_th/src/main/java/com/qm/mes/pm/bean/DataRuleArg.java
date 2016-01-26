package com.qm.mes.pm.bean;
/**
 * 实体Bean用于规则参数的实体信息
 * @author Xujia
 *
 */
public class DataRuleArg {

	/**
	 * 序号
	 */
	private int id;
	/**
	 * 规则号
	 */
	private int int_dataruleid;
	/**
	 * 名称
	 */
	private String name; 
	/**
	 * 当前参数的描述
	 */
	private String description; 
	
	
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
	 * @return int_dateruleid
	 */
	public int getInt_dataruleid() {
		return int_dataruleid;
	}
	/**
	 * @param int_dateruleid 要设置的 int_dateruleid
	 */
	public void setInt_dataruleid(int int_dataruleid) {
		this.int_dataruleid = int_dataruleid;
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

	

}
