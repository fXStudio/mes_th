package com.qm.mes.ra.dao;

import com.qm.mes.ra.bean.ConversionState;
public class DAO_ConversionStateForOracle implements DAO_ConversionState{
	/**
	 * 查询状态转换信息
	 * 
	 * @return sql语句<br>
	 *         int_id 序号 <br>
	 *         INT_FROMSTATE 原状态号<br>
	 *         INT_TOSTATE 跳转状态号<br>
	 *         STR_DESC 描述信息<br>
	 */
	public String getAllConversionState() {
		String sql = "select sc.*,fs.str_statename as fs_str_statename,ts.str_statename as ts_statename"
                    +" from t_ra_stateconversion sc inner join t_ra_state fs on sc.int_fromstate = fs.int_id"
                    +" inner join t_ra_state ts on sc.int_tostate = ts.int_id ";
						return sql;
	}
	/**
	 * 创建Conversionstate
	 * 
	 * @param conversionstate
	 * @return 创建conversionstate的sql语句
	 */
	public String saveConversionState(ConversionState ConversionState) {
		String sql = "insert into t_ra_stateconversion(int_id,int_fromstate,int_tostate,str_desc) "
			+ "values(seq_ra_StateConversion.nextval,'"
			+ ConversionState.getFromstate()
			+ "','" + ConversionState.getTostate()+ "','" + ConversionState.getDesc() + "')";
	return sql;
	}

	/**
	 * 通过序号查询conversionstate
	 * 
	 * @param id
	 * @return 查询conversionstate的sql语句 <br>
	 *         int_id 序号 <br>
	 *         INT_FROMSTATE 原状态号<br>
	 *         INT_TOSTATE 跳转状态号<br>
	 *         STR_DESC 描述信息<br>
	 */
	public String getConversionStateById(int id) {
		String sql = "select int_id,int_fromstate,int_tostate,str_desc from t_ra_stateconversion"
			+ " where int_id = " + id;
		
	return sql;
	}

	/**
	 * 通过序号删除conversion
	 * 
	 * @param id
	 * @return 删除conversionstate的sql语句
	 */
	public String delConversionStateById(int id) {
		String sql = "delete from t_ra_stateconversion where int_id="+ id;
		//System.out.println(sql);
		return sql;
	}
	
	/**
	 * 更新conversionstate对象，通过其id
	 * 
	 * @param conversionstate
	 * @return 更新conversionstate的sql语句
	 */
	public String updateConversionState(ConversionState ConversionState) {
		String sql = "update t_ra_stateconversion set int_fromstate ='"
			+ConversionState.getFromstate()+ "',int_tostate ='"+ConversionState.getTostate()
			+"',str_desc ='"+ ConversionState.getDesc() + "' where int_id ="
			+ConversionState.getId();
		//System.out.println(sql);
	return sql;
		}
	/**
	 * 通过名称查询conversionstate
	 * 
	 * @param str_desc
	 * @return 查询特定规则的sql语句
	 */
	 public String getConversionStateByDesc(String str_desc) {
	
		String sql = "select int_id,int_fromstate,int_tostate,str_desc from t_ra_stateconversion"
				+ " where str_desc='" + str_desc +"'order by int_id asc";
		return sql;
	}
	 
	 /**
	  * 通过生产单元ID查询状态规则
	  * 
	  * @param ProduceUnitId 生产单元号
	  * @return
	  */
	 public String getConversionStateByProdUnitId(int ProduceUnitId){
		 String sql = "select s.int_id,s.int_fromstate,s.int_tostate "+
		 "from t_ra_stateconversion s,t_ra_gatherstaterule r,t_tg_gather g "+ 
		 "where g.INT_PRODUNITID="+ProduceUnitId+" and r.int_gatherid=g.int_id and r.int_stateconversionid=s.int_id";
		
		 return sql;
	 }
}
