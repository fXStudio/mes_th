package mes.pm.dao;

import mes.pm.bean.ExceptionCause;
import mes.pm.bean.ExceptionType;
/**
 * @author Xujia
 *
 */
public class DAO_ExceptionForOracle implements DAO_Exception {
	/**
	 * 创建异常类型的sql语句
	 * @param exType
	 */  
	public String saveExceptionType (ExceptionType exType){
		 String sql = "insert into t_pm_exceptiontype(int_id,int_state,int_sysdefault,str_name) "
				+ "values(seq_PM_EXCEPTIONTYPE.nextval,"
				+ exType.getState()
				+",0,'"+exType.getName()+"')";	   
		return sql;
	}	
	/**
	 * 通过序号查出异常类型的sql语句
	 * @param id
	 * @return
	 */
	public String getExceptionTypeById(int id){
		String sql = "select * from t_pm_exceptiontype"
			+ " where int_id = " + id + "";
	return sql;
	}
	/**
	 * 通过序号删除异常类型的sql语句
	 * @param id
	 * @return
	 */
	public String delExceptionTypeById(int id){
		String sql = "delete from  t_pm_exceptiontype where int_id=" + id;
		return sql;
	}
	/**
	 * 查询出全部异常类型的sql语句
	 * @return
	 */
	public String getAllExceptionType (){
		String sql = "select *"
			+ " from t_pm_exceptiontype";
	
	return sql;
	}
	/**
	 * 通过名称查出异常类型的sql语句
	 * @param name
	 * @return
	 */
	public String getExceptionTypeByName(String name){
		String sql = "select* from t_pm_exceptiontype"
			+ " where str_name='" + name +"'order by int_id";
	
	return sql;
	}
	/**
	 * 创建异常原因的sql语句
	 * @param exCause
	 * @return
	 */  
	public 	String saveExceptionCause (ExceptionCause exCause){
		 String sql = "insert into t_pm_exceptioncause(int_id,str_name,int_state) "
				+ "values(seq_PM_EXCEPTIONCAUSE.nextval,'"
				+ exCause.getName()
				+"',"+exCause.getState()+")";	   
		return sql;
	}
	/**
	 * 通过序号查出异常原因的sql语句
	 * @param id
	 * @return
	 */
	public String getExceptionCauseById(int id){
		String sql = "select * from t_pm_exceptioncause"
			+ " where int_id = " + id + "";
	return sql;
	}
	/**
	 * 通过序号删除异常原因的sql语句
	 * @param id
	 * @return
	 */
	public String delExceptionCauseById(int id){
		String sql = "delete from  t_pm_exceptioncause where int_id=" + id;
		return sql;
	}
	/**
	 * 查询出全部异常原因的sql语句
	 * @return
	 */
	public String getAllExceptionCause(){
		String sql = "select *"
			+ " from t_pm_exceptioncause";
	
	return sql;
	}
	/**
	 * 更新ExceptionType对象，通过其id属性
	 *  徐嘉
	 * @param ExceptionType
	 * @return 更新ExceptionType的sql语句
	 */
	public String updateExceptionType(ExceptionType exceptionType) {  
		String sql = "update t_pm_exceptiontype set int_state ="
			+exceptionType.getState() 
			+ ",str_name= '" +exceptionType.getName() + "' where int_id = "
			+ exceptionType.getId();
	return sql;
	}
	/**
	 * 更新ExceptionCause对象，通过其id属性
	 *  徐嘉
	 * @param ExceptionCause
	 * @return 更新ExceptionCause的sql语句
	 */
	public String updateExceptionCause(ExceptionCause exceptionCause) {
		String sql = "update t_pm_exceptioncause set int_state ="
			+exceptionCause.getState() 
			+ ",str_name= '" +exceptionCause.getName() + "' where int_id = "
			+ exceptionCause.getId();
	return sql;
	}
	/**
	 * 检测ExceptionRecord表中是否有异常类型号
	 *  徐嘉
	 * @param id
	 * @return 关联个数
	 */
	public String checkExceptionTypeById(int id){
		String sql = "select count(*) from  t_pm_exceptiontype a, t_pm_exceptionrecord b"
			+ " where a.int_id=" + id
			+ " and a.int_id = b.int_exceptiontype";
	return sql;
	}
	/**
	 * 检测ExceptionRecord表中是否有异常原因号
	 *  徐嘉
	 * @param id
	 * @return 关联个数
	 */
	public String checkExceptionCauseById(int id){
		String sql = "select count(*) from  t_pm_exceptioncause a, t_pm_exceptionrecord b"	
		+ " where a.int_id=" + id
		+ " and a.int_id = b.int_exceptioncause";
        return sql;
	}

}
