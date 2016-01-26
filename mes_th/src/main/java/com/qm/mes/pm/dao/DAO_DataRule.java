package com.qm.mes.pm.dao;

import com.qm.mes.pm.bean.DataRule;
import com.qm.mes.pm.bean.DataRuleArg;


/**
 * @author Xujia
 *
 */
public interface DAO_DataRule {
	/**
	 * 创建数据规则的sql语句
	 * @param dataRule
	 * @return
	 */
	String saveDataRule(DataRule dataRule);
	/**
	 * 通过序号查出数据规则的sql语句
	 * @param id
	 * @return
	 */
	String getDataRuleById(int id);
	/**
	 * 通过序号查出数据规则参数的sql语句
	 * @param id
	 * @return
	 */
	String getDataRuleArgsById(int id);
	/**
	 * 通过序号删除数据规则的sql语句
	 * @param id
	 * @return
	 */	
	String delDataRuleById(int id);
	/**
	 * 通过序号删除数据规则参数的sql语句
	 * @param id
	 * @return
	 */	
	String delDataRuleArgsById(int id);
	/**
	 * 查询出全部数据规则的sql语句
	 * @return
	 */
	String getAllDataRule();
	/**
	 * 通过名称查出数据规则的sql语句
	 * @param name
	 * @return
	 */
	String getDataRuleByName(String name);
	/**
	 * 更新dataRule对象，通过其id属性
	 *  徐嘉
	 * @param dataRule
	 * @return 更新dataRule的sql语句
	 */
	String updateDataRule(DataRule dataRule); 
	/**
	 * 创建规则参数的sql语句
	 * @param dataRule
	 * @return
	 */
	String saveDataRuleArg(DataRuleArg dataRuleArg);
	
}
