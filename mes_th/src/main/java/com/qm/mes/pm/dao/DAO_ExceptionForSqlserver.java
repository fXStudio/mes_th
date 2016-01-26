package com.qm.mes.pm.dao;

import com.qm.mes.pm.bean.ExceptionCause;
import com.qm.mes.pm.bean.ExceptionType;
/**
 * @author Xujia
 *
 */
public class DAO_ExceptionForSqlserver extends DAO_ExceptionForOracle {
	public String saveExceptionType (ExceptionType exType){
		 String sql = "insert into t_pm_exceptiontype(int_state,int_sysdefault,str_name) "
				+ "values("
				+ exType.getState()
				+","+exType.getSysdefault()+",'"+exType.getName()+"')";	   
		return sql;
	}	
	public 	String saveExceptionCause (ExceptionCause exCause){
		 String sql = "insert into t_pm_exceptioncause(str_name,int_state) "
				+ "values('"
				+ exCause.getName()
				+"',"+exCause.getState()+")";	   
		return sql;
	}

}
