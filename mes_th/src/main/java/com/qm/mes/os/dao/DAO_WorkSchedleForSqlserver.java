package com.qm.mes.os.dao;
import com.qm.mes.os.bean.WorkSchedle;

public class DAO_WorkSchedleForSqlserver extends DAO_WorkSchedleForOracle {
	/** 创建工作时刻表
	 * 包金旭
	 * 
	 */
	
	public String saveWorkSchedle(WorkSchedle  workSchedle){
		String sql = "insert into t_os_workSchedle(int_id,int_produnitid,str_workOrder,str_workSchedle,str_advanceTime)"
			+ "values("	+ workSchedle.getProdunitid()+""
			+ ",'"+ workSchedle.getWorkOrder()+ "'"
			+ ",'"+ workSchedle.getWorkSchedle()+ "'"
			+ ",'"+ workSchedle.getAdvanceTime()+"')";
			
		return sql;
	}


}
