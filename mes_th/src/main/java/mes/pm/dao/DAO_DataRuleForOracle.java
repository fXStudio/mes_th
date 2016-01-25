package mes.pm.dao;

import mes.pm.bean.DataRule;
import mes.pm.bean.DataRuleArg;


/**
 * @author Xujia
 *
 */
public class DAO_DataRuleForOracle implements DAO_DataRule {

	/**
	 * 创建数据规则的sql语句
	 * @param dataRule
	 * @return
	 */
	public String saveDataRule (DataRule dataRule){
		 String sql = "insert into t_pm_datarule(int_id,str_name,str_code,string_rule,str_description) "
			+ "values(seq_PM_DATARULE.nextval,'"
			+ dataRule.getName()
			+"','"+dataRule.getCode()+"','"+dataRule.getRule()+"','"+dataRule.getDescription()+"')";
   
	return sql;
	}
	/**
	 * 创建规则参数的sql语句
	 * @param dataRule
	 * @return
	 */
	public String saveDataRuleArg(DataRuleArg dataRuleArg){
		 String sql = "insert into t_pm_dataruleargs(int_id,str_argname,str_description,int_dataruleid) "
				+ "values(seq_PM_DATARULEARGS.nextval,'"
				+ dataRuleArg.getName()
				+"','"+dataRuleArg.getDescription()+"',"+dataRuleArg.getInt_dataruleid()+")";
	   
		return sql;		
	}
	
	/**
	 * 通过序号查出数据规则的sql语句
	 * @param id
	 * @return
	 */
	public String getDataRuleById(int id){
		String sql = "select * from t_pm_datarule "
			+ "where int_id = " + id + " order by int_id";
	return sql;
		
	}
	/**
	 * 通过序号查出数据规则参数的sql语句
	 * @param id
	 * @return
	 */
	public String getDataRuleArgsById(int id){
		String sql = "select * from t_pm_dataruleargs "
			+ "where int_dataruleid = " + id + " order by int_id";
	return sql;
		
	}
	/**
	 * 通过序号删除数据规则的sql语句
	 * @param id
	 * @return
	 */
	public String delDataRuleById(int id){
		String sql = "delete from  t_pm_datarule where int_id=" + id;
		return sql;
		
	}
	/**
	 * 通过序号删除数据规则参数的sql语句
	 * @param id
	 * @return
	 */	
	public String delDataRuleArgsById(int id){
		String sql="delete from t_pm_dataruleargs where int_dataruleid="+id;
		return sql;
	}
	/**
	 * 查询出全部数据规则的sql语句
	 * @return
	 */
	public String getAllDataRule(){
		String sql = "select *"
			+ " from t_pm_datarule";
	
	return sql;
		
	}
	/**
	 * 通过名称查出数据规则的sql语句
	 * @param name
	 * @return
	 */
	public String getDataRuleByName(String name){
		String sql = "select* from t_pm_datarule"
			+ " where str_name='" + name +"'order by int_id";
	
	return sql;
		
	}
	/**
	 * 更新dataRule对象，通过其id属性
	 *  徐嘉
	 * @param dataRule
	 * @return 更新dataRule的sql语句
	 */
	public String updateDataRule(DataRule dataRule) {		
		String sql = "update t_pm_datarule set str_name ='"
				+dataRule.getName() + "' , str_code = '"+ dataRule.getCode()
				+ "',string_rule ='" + dataRule.getRule()
				+ "', str_description= '" +dataRule.getDescription() + "' where int_id = "
				+ dataRule.getId();
		return sql;
	}


	
}
