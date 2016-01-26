package com.qm.mes.pm.bean;

import java.util.List;
/**
 * 实体Bean用于封装设备的实体信息
 * @author Xujia
 *
 */
public class Device {

	/**
	 *  序号
	 */
	private int id;  
	/**
	 *  名称
	 */
	private String name; 
	/**
	 *  编号，用户可以给相关设备定义编号	
	 */
	private String code ; 
	/**
	 *  当前设备所属类别，与设备类别关联	
	 */
	private List<Integer> types = null; 
	/**
	 * 当前设备的功能描述
	 */
	String description;  
	
	
	/**
	 * @return code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code 要设置的 code
	 */
	public void setCode(String code) {
		this.code = code;
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
	 * @return types
	 */
	public List<Integer> getTypes() {
		return types;
	}
	/**
	 * @param types 要设置的 types
	 */
	public void setTypes(List<Integer> types) {
		this.types = types;
	}
	
	
	

}
