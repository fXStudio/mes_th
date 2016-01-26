package com.qm.mes.ra.dao;

import com.qm.mes.ra.bean.State;


public class DAO_StateForSqlserver extends  DAO_StateForOracle {
	/**
	 * 创建state
	 * 徐嘉
	 * @param state
	 * @return 创建state的sql语句
	 */
	public String saveState(State state) {
		String sql = "insert into t_ra_state(str_statename,str_style,int_delete,str_styledesc) "
				+ "values('"
				+state.getStateName()
				+"','"+state.getStyle()+"',"+state.getDelete()+",'"+state.getStyledesc()+"')";
	   
		return sql;
	}
	/**
	 *插入采集点状态规则
	 * 
	 * 谢静天
	 */
	public String saveGatherStateRule(int int_gatherid,int Stateconversionid,int defaultgo){
		String sql="insert into t_ra_gatherstaterule(int_gatherid,int_stateconversionid,defaultexcute)"
			+" values("
			+ int_gatherid 
			+","
			+ Stateconversionid 
			+","
			+ defaultgo
			+")";
		
		return sql;
}
}
