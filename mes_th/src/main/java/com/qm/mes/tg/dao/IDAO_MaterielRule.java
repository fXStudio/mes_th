package com.qm.mes.tg.dao;

import com.qm.mes.tg.bean.MaterielRule;

public interface IDAO_MaterielRule {

	/**
	 * 通过采集点序号，查出该采集点物料子项的验证规则列表
	 * 
	 * @param id
	 *            采集点序号 <br>
	 * @return 1、int_gatherid 采集点号<br>
	 *         2、int_materielruleid 子物料标识值(条形码)<br>
	 *         3、int_order 采集顺序号<br>
	 *         4、int_id 物料标识规则号<br>
	 *         5、str_name 物料标识规则名<br>
	 *         6、str_validateclass 物料标识规则验证字符串 <br>
	 *         7、desc 验证字符串的描述信息
	 */
	String getAttributeByGid(int id);

	/**
	 * 通过采集点序号，查出该采集点主物料标识规则
	 * 
	 * @param id
	 *            采集点序号
	 * @return 1、int_id 物料标识号<br>
	 *         2、str_name 物料标识名<br>
	 *         3、str_validateclass 验证字符串 <br>
	 *         4、desc 验证字符串的描述信息
	 */
	String gerMainRuleByGid(int id);

	/**
	 * 添加物料标识规则
	 * 
	 * @param materielRule
	 *            实体对象
	 * @return 创建materielRule的sql语句
	 */
	String saveMainRule(MaterielRule materielRule);

	/**
	 * 修改物料标识规则
	 * 
	 * @param materielRule
	 *            实体对象
	 * @return 修改materielRule的sql语句
	 */
	String updateMainRule(MaterielRule materielRule);

	/**
	 * 删除物料标识规则
	 * 
	 * @param id
	 *            物料标识id
	 * @return 删除materielRule的sql语句
	 */
	String deleteMainRule(int id);

	/**
	 * 查询单个物料标识规则
	 * 
	 * @param id
	 *            物料标识id
	 * @return 1、int_id 物料标识号<br>
	 *         2、str_name 物料标识名<br>
	 *         3、str_validateclass 验证字符串 <br>
	 *         4、desc 验证字符串的描述信息
	 */
	String findMainRule(int id);

	/**
	 * 查询物料标识规则
	 * 
	 * @return 1、int_id 物料标识号<br>
	 *         2、str_name 物料标识名<br>
	 *         3、str_validateclass 验证字符串 <br>
	 *         4、desc 验证字符串的描述信息
	 */
	String selectMainRule();

    /**
     * 通过倒叙查询物料标识规则
	 *
	 * @return 1、int_id 物料标识号<br>
	 *         2、str_name 物料标识名<br>
	 *         3、str_validateclass 验证字符串 <br>
	 *         4、desc 验证字符串的描述信息
     */
    String selectMainRuleDESC();

	/**
	 * 查询单个物料标识规则
	 * 
	 * @param name
	 *            物料标识name
	 * @return 1、int_id 物料标识号<br>
	 *         2、str_name 物料标识名<br>
	 *         3、str_validateclass 验证字符串 <br>
	 *         4、desc 验证字符串的描述信息
	 */
	String findMainRuleByName(String name);

	/**
	 * 检测采集点gather表中是否有此物料标识规则号的关联
	 * 
	 * @param id
	 *            物料标识规则id <br>
	 * @return 关联的个数
	 */
	String checkGather(int id);

	/**
	 * 检测采集点gatherItem表中是否有此物料标识规则号的关联
	 * 
	 * @param id
	 *            物料标识规则id <br>
	 * @return 关联的个数
	 */
	String checkGatherItem(int id);
	
}
