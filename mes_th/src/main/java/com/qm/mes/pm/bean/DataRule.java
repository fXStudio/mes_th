package com.qm.mes.pm.bean;

import java.util.List;
/**
 * 实体Bean用于数据规则的实体信息
 * @author Xujia
 *
 */
public class DataRule {

	/**
	 * 　序号
	 */
	private int id; 
	/**
	 * 规则名称
	 */
	private String name; 
	/**
	 * 规则编号
	 */
	private String code; 
	/**
	 * 规则的参数信息
	 */
	private List<DataRuleArg> args;  
	/**
	 *   规则公式
	 */
	private String rule;  
	/**
	 * 当前规则的功能描述
	 */
	private String description;
	
	
	/**
	 * @return args
	 */
	public List<DataRuleArg> getArgs() {
		return args;
	}
	/**
	 * @param args 要设置的 args
	 */
	public void setArgs(List<DataRuleArg> args) {
		this.args = args;
	}
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
	 * @return rule
	 */
	public String getRule() {
		return rule;
	}
	/**
	 * @param rule 要设置的 rule
	 */
	public void setRule(String rule) {
		this.rule = rule;
	}
	
	

}
