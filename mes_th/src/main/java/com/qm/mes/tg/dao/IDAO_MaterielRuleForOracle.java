package com.qm.mes.tg.dao;

import com.qm.mes.tg.bean.MaterielRule;

public class IDAO_MaterielRuleForOracle implements IDAO_MaterielRule {

	/**
	 * 通过采集点序号，查出该采集点物料子项的验证规则列表
	 * 
	 * @param id
	 *            采集点序号
	 * @return 查询子物料验证规则列表的sql语句
	 */
	public String gerMainRuleByGid(int id) {
		String sql = "select int_id,str_name,str_validateclass,str_desc from t_tg_MaterielRule"
				+ " where int_id = (select int_materielruleid from t_tg_Gather where int_id = "
				+ id + " )";
		return sql;
	}

	/**
	 * 通过采集点序号，查出该采集点主物料的验证规则
	 * 
	 * @param id
	 *            采集点序号
	 * @return 查询主物料规则的sql语句
	 */
	public String getAttributeByGid(int id) {
		
		String sql = "select a.int_gatherid ,a.int_materielruleid,a.int_order,b.int_id ,b.str_name,b.str_validateclass,b.str_desc"
				+ "  from t_tg_GatherItem a ,t_tg_MaterielRule b where a.int_materielruleid=b.int_id and a.int_gatherid="
				+ id + " order by a.int_order";
		return sql;
	}

	public String deleteMainRule(int id) {
		String sql = "delete from t_tg_MaterielRule where int_id=" + id;
		return sql;
	}

	public String saveMainRule(MaterielRule materielRule) {
		String sql = "insert into t_tg_MaterielRule(int_id,str_name,str_validateclass,str_desc) "
				+ "values(seq_tg_materielrule.nextval,'"
				+ materielRule.getName()
				+ "','"
				+ materielRule.getValidate()
				+ "','" + materielRule.getDesc() + "')";
		return sql;
	}

	public String updateMainRule(MaterielRule materielRule) {
		String sql = "update t_tg_MaterielRule set str_name = '"
				+ materielRule.getName() + "' , str_validateclass = '"
				+ materielRule.getValidate() + "',str_desc = '"
				+ materielRule.getDesc() + "' where int_id = "
				+ materielRule.getId();
		return sql;
	}

	public String findMainRule(int id) {
		String sql = "select int_id,str_name,str_validateclass,str_desc from t_tg_MaterielRule"
				+ " where int_id = " + id + " order by int_id";
		return sql;
	}

	public String selectMainRule() {
		String sql = "select int_id,str_name,str_validateclass,str_desc "
				+ "from t_tg_MaterielRule ";
		return sql;
	}

    public String selectMainRuleDESC() {
		String sql = "select int_id,str_name,str_validateclass,str_desc "
				+ "from t_tg_MaterielRule order by int_id desc";
		return sql;
	}

	public String checkGather(int id) {
		String sql = "select count(*) from  T_TG_MATERIELRULE a, T_TG_GATHER b"
				+ " where a.int_id=" + id
				+ " and a.int_id = b.INT_MATERIELRULEID";
		return sql;
	}

	public String checkGatherItem(int id) {
		String sql = "select count(*) from  T_TG_MATERIELRULE a, T_TG_GATHERITEM b"
				+ " where a.int_id="
				+ id
				+ " and a.int_id = b.INT_MATERIELRULEID";
		return sql;
	}

	public String findMainRuleByName(String name) {
		String sql = "select int_id,str_name,str_validateclass,str_desc from t_tg_MaterielRule"
				+ " where str_name = '" + name + "' order by int_id";
		return sql;
	}

}
