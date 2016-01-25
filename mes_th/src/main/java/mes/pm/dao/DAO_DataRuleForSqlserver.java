package mes.pm.dao;

import mes.pm.bean.DataRule;
import mes.pm.bean.DataRuleArg;

/**
 * @author Xujia
 *
 */
public class DAO_DataRuleForSqlserver extends DAO_DataRuleForOracle {
	/**
	 * 创建数据规则的sql语句
	 * @param dataRule
	 * @return
	 */
	public String saveDataRule (DataRule dataRule){
		final String sql = "insert into t_pm_datarule(str_name,str_code,string_rule,str_description) "
			+ "values('"
			+ dataRule.getName()
			+"','"+dataRule.getCode()+"',"+dataRule.getRule()+",'"+dataRule.getDescription()+"')";
   
	return sql;
}
	public String saveDataRuleArg(DataRuleArg dataRuleArg){
		 String sql = "insert into t_pm_dataruleargs(str_argname,str_description,int_dataruleid) "
				+ "values('"
				+ dataRuleArg.getName()
				+"','"+dataRuleArg.getDescription()+"',"+dataRuleArg.getInt_dataruleid()+")";
	   
		return sql;		
	}
}
